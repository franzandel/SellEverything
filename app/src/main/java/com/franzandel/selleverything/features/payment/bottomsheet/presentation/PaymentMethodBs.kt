package com.franzandel.selleverything.features.payment.bottomsheet.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.franzandel.selleverything.R
import com.franzandel.selleverything.base.presentation.BaseBottomSheet
import com.franzandel.selleverything.features.payment.data.PaymentList
import com.franzandel.selleverything.features.payment.data.entity.PaymentMethod
import kotlinx.android.synthetic.main.bs_payment_method.*

/**
 * Created by Franz Andel on 01/10/20.
 * Android Engineer
 */

class PaymentMethodBs : BaseBottomSheet() {

    private val _onPaymentContentClicked = MutableLiveData<PaymentMethod>()
    val onPaymentContentClicked: LiveData<PaymentMethod> = _onPaymentContentClicked

    private val adapter by lazy {
        PaymentMethodBsAdapter(requireContext())
    }

    private val vm by lazy {
        ViewModelProvider(this).get(PaymentMethodBsVM::class.java)
    }

    override fun getLayoutId(): Int = R.layout.bs_payment_method

    override fun onActivityReady() {
        setupRV()
        setupObserver()
        setupUIClickListener()
        vm.processPaymentMethodsMultiType(PaymentList.getPaymentMethods())
    }

    private fun setupRV() {
        rvPaymentMethod.adapter = adapter
    }

    private fun setupObserver() {
        vm.paymentMethodsMultiType.observe(viewLifecycleOwner, Observer { paymentMethodsMultiType ->
            adapter.setData(paymentMethodsMultiType)
        })

        adapter.onPaymentContentClicked.observe(viewLifecycleOwner, Observer { paymentMethod ->
            _onPaymentContentClicked.value = paymentMethod
            dismiss()
        })
    }

    private fun setupUIClickListener() {
        ivPaymentMethodClose.setOnClickListener {
            dismiss()
        }
    }
}