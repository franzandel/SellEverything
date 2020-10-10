package com.franzandel.selleverything.features.payment.bottomsheet.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.R
import com.franzandel.selleverything.features.payment.bottomsheet.data.entity.PaymentMethodMultiType
import com.franzandel.selleverything.features.payment.bottomsheet.data.enums.PaymentMethodSection
import com.franzandel.selleverything.features.payment.data.entity.PaymentMethod

/**
 * Created by Franz Andel on 01/10/20.
 * Android Engineer
 */

class PaymentMethodBsAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_CONTENT = 1
    }

    private val _onPaymentContentClicked = MutableLiveData<PaymentMethod>()
    val onPaymentContentClicked: LiveData<PaymentMethod> = _onPaymentContentClicked

    private val activity = context as AppCompatActivity

    private lateinit var paymentMethodBsContentViewHolder: PaymentMethodBsContentViewHolder

    private var currentList = listOf<PaymentMethodMultiType<PaymentMethod>>()

    fun setData(currentList: List<PaymentMethodMultiType<PaymentMethod>>) {
        this.currentList = currentList
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position].section) {
            PaymentMethodSection.HEADER -> TYPE_HEADER
            PaymentMethodSection.CONTENT -> TYPE_CONTENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        return when (viewType) {
            TYPE_HEADER -> {
                val view =
                    layoutInflater.inflate(R.layout.item_payment_method_header, parent, false)
                PaymentMethodBsHeaderViewHolder(view)
            }
            else -> {
                val view =
                    layoutInflater.inflate(R.layout.item_payment_method_content, parent, false)
                paymentMethodBsContentViewHolder = PaymentMethodBsContentViewHolder(view)
                setupPaymentMethodContentObserver()
                paymentMethodBsContentViewHolder
            }
        }
    }

    private fun setupPaymentMethodContentObserver() {
        paymentMethodBsContentViewHolder.onPaymentContentClicked.observe(
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

    override fun getItemCount(): Int = currentList.size
}