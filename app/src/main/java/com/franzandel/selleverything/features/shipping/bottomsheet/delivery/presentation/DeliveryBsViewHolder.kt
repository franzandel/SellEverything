package com.franzandel.selleverything.features.shipping.bottomsheet.delivery.presentation

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.data.constants.StringConstants
import com.franzandel.selleverything.extension.getFormattedIDNPrice
import com.franzandel.selleverything.features.shipping.bottomsheet.delivery.data.entity.Delivery
import kotlinx.android.synthetic.main.item_delivery.view.*

/**
 * Created by Franz Andel on 27/09/20.
 * Android Engineer
 */

class DeliveryBsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val _onClicked = MutableLiveData<Delivery>()
    val onClicked: LiveData<Delivery> = _onClicked

    fun bind(delivery: Delivery) {
        itemView.apply {
            tvDeliveryItemTitle.text = delivery.type
            setupDeliveryItemPrice(delivery)

            setOnClickListener {
                onItemClicked(delivery)
            }
        }
    }

    private fun setupDeliveryItemPrice(delivery: Delivery) {
        val deliveryItemPrice = if (delivery.minPrice.isEmpty()) {
            StringConstants.FREE
        } else {
            val formattedMinPrice = delivery.minPrice.toInt().getFormattedIDNPrice()
            if (delivery.minPrice == delivery.maxPrice) {
                formattedMinPrice
            } else {
                val formattedMaxPrice = delivery.maxPrice.toInt().getFormattedIDNPrice()
                "$formattedMinPrice - $formattedMaxPrice"
            }
        }
        itemView.tvDeliveryItemPrice.text = deliveryItemPrice
    }

    private fun onItemClicked(delivery: Delivery) {
        if (delivery.couriers.isNotEmpty()) {
            delivery.couriers[0].isChecked = true
        }
        _onClicked.value = delivery
    }
}