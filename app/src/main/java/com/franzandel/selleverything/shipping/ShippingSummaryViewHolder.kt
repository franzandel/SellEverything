package com.franzandel.selleverything.shipping

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.R
import com.franzandel.selleverything.data.constants.NumberConstants
import com.franzandel.selleverything.data.entity.ShippingSummary
import com.franzandel.selleverything.extension.getFormattedIDNPrice
import com.franzandel.selleverything.extension.hide
import com.franzandel.selleverything.extension.show
import kotlinx.android.synthetic.main.item_shipping_summary.view.*

/**
 * Created by Franz Andel on 26/09/20.
 * Android Engineer
 */

class ShippingSummaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val _onShippingChoosePaymentClicked = MutableLiveData<Unit>()
    val onShippingChoosePaymentClicked: LiveData<Unit> = _onShippingChoosePaymentClicked

    fun bind(any: Any?) {
        val shippingSummary = any as? ShippingSummary
        itemView.apply {
            tvShippingTotalProductsPriceTitle.text =
                context.getString(
                    R.string.shipping_total_products_price_title,
                    shippingSummary?.totalQty.toString()
                )

            tvShippingTotalProductsPrice.text =
                shippingSummary?.totalPrice?.toLong()?.getFormattedIDNPrice()
            setupTvShippingSummaryPrice(shippingSummary)

            if (shippingSummary?.totalShippingPrice == NumberConstants.ZERO) {
                tvTotalShippingPriceTitle.hide()
                tvTotalShippingPrice.hide()
            } else {
                tvTotalShippingPrice.text =
                    shippingSummary?.totalShippingPrice?.toLong()?.getFormattedIDNPrice()
                tvTotalShippingPriceTitle.show()
                tvTotalShippingPrice.show()
            }

            btnShippingChoosePayment.setOnClickListener {
                _onShippingChoosePaymentClicked.value = Unit
            }
        }
    }

    private fun setupTvShippingSummaryPrice(shippingSummary: ShippingSummary?) {
        val totalPrice = shippingSummary?.totalPrice?.toLong() ?: 0
        val totalShippingPrice = shippingSummary?.totalShippingPrice?.toLong() ?: 0
        val totalShippingSummaryPrice = totalPrice + totalShippingPrice

        itemView.tvShippingSummaryPrice.text = totalShippingSummaryPrice.getFormattedIDNPrice()
    }
}