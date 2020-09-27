package com.franzandel.selleverything.shipping

import android.view.View
import androidx.appcompat.app.AppCompatActivity
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

    fun bind(product: Product?) {
        itemView.apply {
            setupShippingFooterSubTotal(product)

            tvShippingFooterDelivery.setOnClickListener {
                onShippingFooterDeliveryClicked()
            }

            tvShippingFooterDeliveryCourier.setOnClickListener {
                onShippingFooterDeliveryCourierClicked()
            }
        }
    }

    private fun setupShippingFooterSubTotal(product: Product?) {
        itemView.tvShippingFooterSubTotal.text =
            product?.price?.toLong()?.getFormattedIDNPrice() ?: NumberConstants.DASH
    }

    private fun onShippingFooterDeliveryClicked() {
        itemView.apply {
            val activity = context as AppCompatActivity
            val deliveryBottomSheet = DeliveryBottomSheet()

            deliveryBottomSheet.onClicked.observe(activity, Observer { delivery ->
                removeDrawableStartFromTvShippingFooterDelivery()
                tvShippingFooterDelivery.text = delivery.type
                setupTvShippingFooterDeliveryCourier(delivery)
            })

            deliveryBottomSheet.show(activity.supportFragmentManager, deliveryBottomSheet.tag)
        }
    }

    private fun setupTvShippingFooterDeliveryCourier(delivery: Delivery) {
        itemView.apply {
            if (delivery.minPrice.isNotEmpty()) {
                val firstCourier = delivery.couriers[0]
                val formattedPrice = firstCourier.price.toLong().getFormattedIDNPrice()
                tvShippingFooterDeliveryCourier.text = context.getString(
                    R.string.shipping_footer_delivery_courier_text,
                    firstCourier.name,
                    formattedPrice
                )

                tvShippingFooterDeliveryCourier.show()
                couriers = delivery.couriers
            } else {
                tvShippingFooterDeliveryCourier.hide()
            }
        }
    }

    private fun removeDrawableStartFromTvShippingFooterDelivery() {
        itemView.tvShippingFooterDelivery.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.ic_baseline_chevron_right_24,
            0
        )
    }

    private fun onShippingFooterDeliveryCourierClicked() {
        itemView.apply {
            val activity = context as AppCompatActivity
            checkFirstCourierIfNothingIsChecked()

            val courierBottomSheet = CourierBottomSheet(couriers)

            courierBottomSheet.onClicked.observe(activity, Observer { courier ->
                val formattedPrice = courier.price.toLong().getFormattedIDNPrice()
                tvShippingFooterDeliveryCourier.text = context.getString(
                    R.string.shipping_footer_delivery_courier_text,
                    courier.name,
                    formattedPrice
                )
            })

            courierBottomSheet.show(activity.supportFragmentManager, courierBottomSheet.tag)
        }
    }

    private fun checkFirstCourierIfNothingIsChecked() {
        if (!couriers.any { it.isChecked }) {
            couriers[0].isChecked = true
        }
    }
}