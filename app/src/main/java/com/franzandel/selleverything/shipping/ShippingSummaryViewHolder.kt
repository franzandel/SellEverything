package com.franzandel.selleverything.shipping

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.R
import com.franzandel.selleverything.data.constants.NumberConstants
import com.franzandel.selleverything.extension.showToast
import com.franzandel.selleverything.newest.Product
import kotlinx.android.synthetic.main.item_shipping_summary.view.*

/**
 * Created by Franz Andel on 26/09/20.
 * Android Engineer
 */

class ShippingSummaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(product: Product?) {
        itemView.apply {
            tvShippingTotalProductsPriceTitle.text =
                context.getString(
                    R.string.shipping_total_products_price_title,
                    product?.currentQty.toString()
                )

            tvShippingTotalProductsPrice.text = product?.price
            tvShippingTotalPrice.text = NumberConstants.DASH

            btnShippingChoosePayment.setOnClickListener {
                // TODO: Handle Payment Page
                context.showToast("Go to Payment")
            }
        }
    }
}