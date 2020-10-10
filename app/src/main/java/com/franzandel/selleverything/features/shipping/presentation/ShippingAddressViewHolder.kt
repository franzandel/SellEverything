package com.franzandel.selleverything.features.shipping.presentation

import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.features.shipping.data.entity.ShippingAddress
import kotlinx.android.synthetic.main.item_shipping_address.view.*

/**
 * Created by Franz Andel on 26/09/20.
 * Android Engineer
 */

class ShippingAddressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(any: Any?) {
        val shippingAddress = any as? ShippingAddress
        itemView.apply {
            etShippingAddress.setText(shippingAddress?.address)

            etShippingAddress.addTextChangedListener { address ->
                shippingAddress?.address = address.toString()
            }
        }
    }
}