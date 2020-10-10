package com.franzandel.selleverything.features.payment.bottomsheet.presentation

import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.R
import com.franzandel.selleverything.base.BaseAdapter
import com.franzandel.selleverything.features.payment.bottomsheet.data.entity.PaymentMethodMultiType
import com.franzandel.selleverything.features.payment.bottomsheet.data.enums.PaymentMethodSection
import com.franzandel.selleverything.features.payment.data.entity.PaymentMethod

/**
 * Created by Franz Andel on 01/10/20.
 * Android Engineer
 */

class PaymentMethodBsAdapter(context: Context) :
    BaseAdapter<RecyclerView.ViewHolder, PaymentMethodMultiType<PaymentMethod>>(context) {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_CONTENT = 1
    }

    private val _onPaymentContentClicked = MutableLiveData<PaymentMethod>()
    val onPaymentContentClicked: LiveData<PaymentMethod> = _onPaymentContentClicked

    private val activity = context as AppCompatActivity
    private var currentList = listOf<PaymentMethodMultiType<PaymentMethod>>()

    override fun getItemLayoutId(): Int =
        when (viewType) {
            TYPE_HEADER -> R.layout.item_payment_method_header
            else -> R.layout.item_payment_method_content
        }

    override fun getViewHolder(view: View): RecyclerView.ViewHolder =
        when (viewType) {
            TYPE_HEADER -> PaymentMethodBsHeaderViewHolder(view)
            else -> PaymentMethodBsContentViewHolder(view)
        }

    override fun getCurrentList(): List<PaymentMethodMultiType<PaymentMethod>> = currentList

    fun setData(currentList: List<PaymentMethodMultiType<PaymentMethod>>) {
        this.currentList = currentList
    }

    override fun getItemViewType(position: Int): Int =
        when (currentList[position].section) {
            PaymentMethodSection.HEADER -> TYPE_HEADER
            PaymentMethodSection.CONTENT -> TYPE_CONTENT
        }

    override fun actionOnCreateViewHolder() {
        if (viewType == TYPE_CONTENT) {
            setupPaymentMethodContentObserver()
        }
    }

    private fun setupPaymentMethodContentObserver() {
        (viewHolder as PaymentMethodBsContentViewHolder).onPaymentContentClicked.observe(
            activity,
            Observer { paymentMethod ->
                _onPaymentContentClicked.value = paymentMethod
            })
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val paymentMultiType = currentList[position]
        when (paymentMultiType.section) {
            PaymentMethodSection.HEADER -> (holder as PaymentMethodBsHeaderViewHolder).bind(
                paymentMultiType.data
            )
            PaymentMethodSection.CONTENT -> (holder as PaymentMethodBsContentViewHolder).bind(
                paymentMultiType.data
            )
        }
    }
}