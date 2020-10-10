package com.franzandel.selleverything.features.shipping.presentation

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.R
import com.franzandel.selleverything.data.entity.Product
import com.franzandel.selleverything.extension.getDiscountedPrice
import com.franzandel.selleverything.extension.getDrawableIdFromName
import com.franzandel.selleverything.extension.getFormattedIDNPrice
import com.franzandel.selleverything.extension.getFormattedWeight
import kotlinx.android.synthetic.main.item_shipping_content.view.*

/**
 * Created by Franz Andel on 26/09/20.
 * Android Engineer
 */

class ShippingContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(any: Any?) {
        val product = any as? Product
        itemView.apply {
            product?.let {
                setupIvShippingContentProduct(context, product)
                tvShippingContentTitle.text = product.title
                setupTvShippingContentQty(context, product)
                tvShippingContentPrice.text =
                    product.price.getDiscountedPrice(product.discountPercentage)
                        .getFormattedIDNPrice()
            }
        }
    }

    private fun setupIvShippingContentProduct(context: Context, product: Product) {
        val drawableId = context.getDrawableIdFromName(product.imageName)
        itemView.ivShippingContentProduct.setImageResource(drawableId)
    }

    private fun setupTvShippingContentQty(context: Context, product: Product) {
        val totalWeight = product.weight.toInt() * product.currentQty
        itemView.tvShippingContentQty.text = context.getString(
            R.string.shipping_content_qty,
            product.currentQty.toString(),
            totalWeight.getFormattedWeight()
        )
    }
}