package com.franzandel.selleverything.payment

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.data.entity.PaymentMethod
import kotlinx.android.synthetic.main.item_payment_method_content.view.*

/**
 * Created by Franz Andel on 01/10/20.
 * Android Engineer
 */

class PaymentMethodBsContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val _onPaymentContentClicked = MutableLiveData<PaymentMethod>()
    val onPaymentContentClicked: LiveData<PaymentMethod> = _onPaymentContentClicked

    fun bind(paymentMethod: PaymentMethod) {
        itemView.apply {
            tvPaymentMethodContentTitle.text = paymentMethod.name

            setOnClickListener {
                _onPaymentContentClicked.value = paymentMethod
            }
        }
    }
}