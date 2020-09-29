package com.franzandel.selleverything.shipping

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.R
import com.franzandel.selleverything.data.constants.NumberConstants
import com.franzandel.selleverything.data.entity.ShippingSummary
import com.franzandel.selleverything.extension.getFormattedIDNPrice
import com.franzandel.selleverything.extension.hide
import com.franzandel.selleverything.extension.show
import com.franzandel.selleverything.extension.showToast
import kotlinx.android.synthetic.main.item_shipping_summary.view.*

/**
 * Created by Franz Andel on 26/09/20.
 * Android Engineer
 */

class ShippingSummaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(any: Any?) {
        val shippingSummary = any as? ShippingSummary
        itemView.apply {
            tvShippingTotalProductsPriceTitle.text =
                context.getString(
                    R.string.shipping_total_products_price_title,
                    shippingSummary?.totalQty.toString()
                )

            tvShippingTotalProductsPrice.text = shippingSummary?.totalPrice
            tvShippingTotalPrice.text = NumberConstants.DASH

            if (shippingSummary?.totalShippingPrice == NumberConstants.ZERO) {
                tvTotalShippingPriceTitle.hide()
                tvShippingPrice.hide()
            } else {
                tvShippingPrice.text =
                    shippingSummary?.totalShippingPrice?.toLong()?.getFormattedIDNPrice()
                tvTotalShippingPriceTitle.show()
                tvShippingPrice.show()
            }

            btnShippingChoosePayment.setOnClickListener {
                // TODO: Handle Payment Page
                context.showToast("Go to Payment")
            }
        }
    }
}