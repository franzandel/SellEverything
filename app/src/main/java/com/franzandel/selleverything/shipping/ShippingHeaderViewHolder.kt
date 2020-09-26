package com.franzandel.selleverything.shipping

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.newest.Product
import kotlinx.android.synthetic.main.item_shipping_header.view.*

/**
 * Created by Franz Andel on 26/09/20.
 * Android Engineer
 */

class ShippingHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(product: Product?) {
        itemView.apply {
            tvShippingHeaderSeller.text = product?.seller
            tvShippingHeaderLocation.text = product?.location
        }
    }
}