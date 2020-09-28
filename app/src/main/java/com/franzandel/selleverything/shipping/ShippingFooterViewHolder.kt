package com.franzandel.selleverything.shipping

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.R
import com.franzandel.selleverything.courier.CourierBottomSheet
import com.franzandel.selleverything.data.constants.NumberConstants
import com.franzandel.selleverything.data.entity.Courier
import com.franzandel.selleverything.data.entity.Delivery
import com.franzandel.selleverything.delivery.DeliveryBottomSheet
import com.franzandel.selleverything.extension.getFormattedIDNPrice
import com.franzandel.selleverything.extension.hide
import com.franzandel.selleverything.extension.show
import com.franzandel.selleverything.newest.Product
import kotlinx.android.synthetic.main.item_shipping_footer.view.*


/**
 * Created by Franz Andel on 26/09/20.
 * Android Engineer
 */

class ShippingFooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var couriers: List<Courier>

    private val _onDeliveryOrCourierClicked = MutableLiveData<String>()
    val onDeliveryOrCourierClicked: LiveData<String> = _onDeliveryOrCourierClicked

    private val _onDeliveryOrCourierFirstClicked = MutableLiveData<String>()
    val onDeliveryOrCourierFirstClicked: LiveData<String> = _onDeliveryOrCourierFirstClicked

    fun bind(any: Any?) {
        val product = any as? Product
        itemView.apply {
            setupShippingFooterSubTotal(product)

            tvShippingFooterDelivery.setOnClickListener {
                onShippingFooterDeliveryClicked(product)
            }

            tvShippingFooterDeliveryCourier.setOnClickListener {
                onShippingFooterDeliveryCourierClicked(product)
            }
        }
    }

    private fun setupShippingFooterSubTotal(product: Product?) {
        itemView.tvShippingFooterSubTotal.text =
            product?.price?.toLong()?.getFormattedIDNPrice() ?: NumberConstants.DASH
    }

    private fun onShippingFooterDeliveryClicked(product: Product?) {
        itemView.apply {
            val activity = context as AppCompatActivity
            val deliveryBottomSheet = DeliveryBottomSheet()

            deliveryBottomSheet.onClicked.observe(activity, Observer { delivery ->
                removeDrawableStartFromTvShippingFooterDelivery()

                var courier: Courier? = null
                if (tvShippingFooterDelivery.text != context.getString(R.string.shipping_footer_delivery)) {
                    val initialCourierPrice = getInitialCourierPrice()
                    _onDeliveryOrCourierFirstClicked.value = initialCourierPrice
                }

                val checkedCouriers = delivery.couriers.filter { it.isChecked }
                if (checkedCouriers.isNotEmpty()) {
                    courier = checkedCouriers[0]
                }

                setupTvShippingFooterSubTotal(product, courier)
                tvShippingFooterDelivery.text = delivery.type
                setupTvShippingFooterDeliveryCourier(delivery)
            })

            deliveryBottomSheet.show(activity.supportFragmentManager, deliveryBottomSheet.tag)
        }
    }

    private fun setupTvShippingFooterDeliveryCourier(delivery: Delivery) {
        itemView.apply {
            if (delivery.minPrice.isNotEmpty()) {
                resetAllCouriers(delivery.couriers)
                val firstCourier = delivery.couriers[0]
                val formattedPrice = firstCourier.price.toLong().getFormattedIDNPrice()
                tvShippingFooterDeliveryCourier.text = context.getString(
                    R.string.shipping_footer_delivery_courier_text,
                    firstCourier.name,
                    formattedPrice
                )

                tvShippingFooterDeliveryCourier.show()
            } else {
                tvShippingFooterDeliveryCourier.hide()
            }
            couriers = delivery.couriers
        }
    }

    private fun resetAllCouriers(couriers: List<Courier>) {
        couriers.forEach { courier ->
            courier.isChecked = false
        }
        couriers[0].isChecked = true
    }

    private fun removeDrawableStartFromTvShippingFooterDelivery() {
        itemView.tvShippingFooterDelivery.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.ic_baseline_chevron_right_24,
            0
        )
    }

    private fun onShippingFooterDeliveryCourierClicked(product: Product?) {
        itemView.apply {
            val activity = context as AppCompatActivity
            checkFirstCourierIfNothingIsChecked()

            val initialCourierPrice = getInitialCourierPrice()
            _onDeliveryOrCourierFirstClicked.value = initialCourierPrice

            val courierBottomSheet = CourierBottomSheet(couriers)

            courierBottomSheet.onClicked.observe(activity, Observer { courier ->
                val formattedPrice = courier.price.toLong().getFormattedIDNPrice()
                tvShippingFooterDeliveryCourier.text = context.getString(
                    R.string.shipping_footer_delivery_courier_text,
                    courier.name,
                    formattedPrice
                )
                setupTvShippingFooterSubTotal(product, courier)
            })

            courierBottomSheet.onCancelClicked.observe(activity, Observer {
                _onDeliveryOrCourierClicked.value = initialCourierPrice
            })

            courierBottomSheet.show(activity.supportFragmentManager, courierBottomSheet.tag)
        }
    }

    private fun getInitialCourierPrice(): String {
        val checkedCouriers = couriers.filter {
            it.isChecked
        }
        return if (checkedCouriers.isNotEmpty()) {
            checkedCouriers[0].price
        } else {
            NumberConstants.ZERO
        }
    }

    private fun setupTvShippingFooterSubTotal(product: Product?, courier: Courier?) {
        itemView.apply {
            val currentSubTotal = product?.price?.toLong() ?: NumberConstants.ZERO.toLong()
            val courierPrice = if (courier?.price?.isEmpty() != false) {
                NumberConstants.ZERO.toLong()
            } else {
                courier.price.toLong()
            }
            val newSubTotal = currentSubTotal + courierPrice
            tvShippingFooterSubTotal.text = newSubTotal.getFormattedIDNPrice()
            _onDeliveryOrCourierClicked.value = courier?.price ?: NumberConstants.ZERO
        }
    }

    private fun checkFirstCourierIfNothingIsChecked() {
        if (!couriers.any { it.isChecked }) {
            couriers[0].isChecked = true
        }
    }
}