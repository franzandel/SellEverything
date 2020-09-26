package com.franzandel.selleverything.shipping

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.extension.getFormattedIDNPrice
import com.franzandel.selleverything.extension.showToast
import com.franzandel.selleverything.newest.Product
import kotlinx.android.synthetic.main.item_shipping_footer.view.*

/**
 * Created by Franz Andel on 26/09/20.
 * Android Engineer
 */

class ShippingFooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(product: Product?) {
        itemView.apply {
            tvShippingFooterSubTotal.text = product?.price?.toLong()?.getFormattedIDNPrice() ?: "-"

            tvShippingFooterDelivery.setOnClickListener {
                // TODO: Handle Delivery Page
                context.showToast("Show Delivery chooser")
            }
        }
    }
}