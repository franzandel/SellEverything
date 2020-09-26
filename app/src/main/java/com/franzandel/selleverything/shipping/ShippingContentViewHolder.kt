package com.franzandel.selleverything.shipping

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.R
import com.franzandel.selleverything.extension.getDiscountedPrice
import com.franzandel.selleverything.extension.getFormattedIDNPrice
import com.franzandel.selleverything.extension.getFormattedWeight
import com.franzandel.selleverything.newest.Product
import kotlinx.android.synthetic.main.item_shipping_content.view.*

/**
 * Created by Franz Andel on 26/09/20.
 * Android Engineer
 */

class ShippingContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(product: Product?) {
        itemView.apply {
            product?.let {
                // TODO: HANDLE IMAGE
                tvShippingContentTitle.text = product.title
                val totalWeight = product.weight.toInt() * product.currentQty
                tvShippingContentQty.text = context.getString(
                    R.string.shipping_content_qty,
                    product.currentQty.toString(),
                    totalWeight.getFormattedWeight()
                )
                tvShippingContentPrice.text =
                    product.price.getDiscountedPrice(product.discountPercentage)
                        .getFormattedIDNPrice()
            }
        }
    }
}