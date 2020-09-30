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
import com.franzandel.selleverything.data.entity.ShippingFooter
import com.franzandel.selleverything.delivery.DeliveryBottomSheet
import com.franzandel.selleverything.extension.getFormattedIDNPrice
import com.franzandel.selleverything.extension.hide
import com.franzandel.selleverything.extension.show
import kotlinx.android.synthetic.main.item_shipping_footer.view.*


/**
 * Created by Franz Andel on 26/09/20.
 * Android Engineer
 */

class ShippingFooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var couriers: List<Courier>

    private val _onDeliveryOrCourierBsSelected = MutableLiveData<String>()
    val onDeliveryOrCourierBsSelected: LiveData<String> = _onDeliveryOrCourierBsSelected

    private val _onDeliveryOrCourierBsOpened = MutableLiveData<String>()
    val onDeliveryOrCourierBsOpened: LiveData<String> = _onDeliveryOrCourierBsOpened

    fun bind(any: Any?) {
        val shippingFooter = any as? ShippingFooter
        itemView.apply {
            shippingFooter?.adapterPosition = adapterPosition
            setupShippingFooterSubTotal(shippingFooter)

            tvShippingFooterDelivery.setOnClickListener {
                onShippingFooterDeliveryClicked(shippingFooter)
            }

            tvShippingFooterDeliveryCourier.setOnClickListener {
                onShippingFooterDeliveryCourierClicked(shippingFooter)
            }
        }
    }

    private fun setupShippingFooterSubTotal(shippingFooter: ShippingFooter?) {
        val totalProductsPrice = shippingFooter?.totalProductsPrice?.toLong() ?: 0
        val shippingPrice = shippingFooter?.shippingPrice?.toLong() ?: 0
        val subTotal = totalProductsPrice + shippingPrice

        itemView.tvShippingFooterSubTotal.text = subTotal.getFormattedIDNPrice()
    }

    private fun onShippingFooterDeliveryClicked(shippingFooter: ShippingFooter?) {
        itemView.apply {
            val activity = context as AppCompatActivity
            val deliveryBottomSheet = DeliveryBottomSheet()
            setupDeliveryBottomSheetObserver(deliveryBottomSheet, shippingFooter)
            deliveryBottomSheet.show(activity.supportFragmentManager, deliveryBottomSheet.tag)
        }
    }

    private fun setupDeliveryBottomSheetObserver(
        deliveryBottomSheet: DeliveryBottomSheet,
        shippingFooter: ShippingFooter?
    ) {
        itemView.apply {
            val activity = context as AppCompatActivity

            deliveryBottomSheet.onClicked.observe(activity, Observer { delivery ->
                shippingFooter?.deliveryType = delivery.type
                removeDrawableStartFromTvShippingFooterDelivery()

                setupShippingPrice(shippingFooter, delivery)
                _onDeliveryOrCourierBsSelected.value = shippingFooter?.shippingPrice

                tvShippingFooterDelivery.text = delivery.type
                setupTvShippingFooterDeliveryCourier(delivery)
            })
        }
    }

    private fun setupShippingPrice(shippingFooter: ShippingFooter?, delivery: Delivery) {
        itemView.apply {
            var courier: Courier? = null
            if (tvShippingFooterDelivery.text != context.getString(R.string.shipping_footer_delivery)) {
                _onDeliveryOrCourierBsOpened.value = shippingFooter?.shippingPrice
            }

            val checkedCouriers = delivery.couriers.filter { it.isChecked }
            if (checkedCouriers.isNotEmpty()) {
                courier = checkedCouriers[0]
            }

            shippingFooter?.shippingPrice = courier?.price ?: NumberConstants.ZERO
        }
    }

    private fun setupTvShippingFooterDeliveryCourier(delivery: Delivery) {
        itemView.apply {
            if (delivery.minPrice.isNotEmpty()) {
                resetCouriersIsChecked(delivery.couriers)
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

    private fun resetCouriersIsChecked(couriers: List<Courier>) {
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

    private fun onShippingFooterDeliveryCourierClicked(shippingFooter: ShippingFooter?) {
        itemView.apply {
            checkFirstCourierIfNothingIsChecked()
            _onDeliveryOrCourierBsOpened.value = shippingFooter?.shippingPrice

            val activity = context as AppCompatActivity
            val courierBottomSheet = CourierBottomSheet(couriers)
            setupCourierBottomSheetObserver(courierBottomSheet, shippingFooter)
            courierBottomSheet.show(activity.supportFragmentManager, courierBottomSheet.tag)
        }
    }

    private fun setupCourierBottomSheetObserver(
        courierBottomSheet: CourierBottomSheet,
        shippingFooter: ShippingFooter?
    ) {
        itemView.apply {
            val activity = context as AppCompatActivity

            courierBottomSheet.onClicked.observe(activity, Observer { courier ->
                val formattedPrice = courier.price.toLong().getFormattedIDNPrice()
                tvShippingFooterDeliveryCourier.text = context.getString(
                    R.string.shipping_footer_delivery_courier_text,
                    courier.name,
                    formattedPrice
                )

                shippingFooter?.shippingPrice = courier?.price ?: NumberConstants.ZERO
                _onDeliveryOrCourierBsSelected.value = shippingFooter?.shippingPrice
            })

            courierBottomSheet.onCancelClicked.observe(activity, Observer {
                _onDeliveryOrCourierBsSelected.value = shippingFooter?.shippingPrice
            })
        }
    }

    private fun checkFirstCourierIfNothingIsChecked() {
        if (!couriers.any { it.isChecked }) {
            couriers[0].isChecked = true
        }
    }
}