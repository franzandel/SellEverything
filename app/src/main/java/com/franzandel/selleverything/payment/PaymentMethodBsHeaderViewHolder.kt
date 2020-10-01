package com.franzandel.selleverything.payment

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.data.entity.PaymentMethod
import kotlinx.android.synthetic.main.item_payment_method_header.view.*

/**
 * Created by Franz Andel on 01/10/20.
 * Android Engineer
 */

class PaymentMethodBsHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(paymentMethod: PaymentMethod) {
        itemView.tvPaymentMethodHeaderTitle.text = paymentMethod.type
    }
}