package com.franzandel.selleverything.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.franzandel.selleverything.R
import com.franzandel.selleverything.data.entity.PaymentMethod
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bs_payment_method_bottom_sheet.*

/**
 * Created by Franz Andel on 01/10/20.
 * Android Engineer
 */

class PaymentMethodBs : BottomSheetDialogFragment() {

    private val _onPaymentContentClicked = MutableLiveData<PaymentMethod>()
    val onPaymentContentClicked: LiveData<PaymentMethod> = _onPaymentContentClicked

    private val adapter by lazy {
        PaymentMethodBsAdapter(requireContext())
    }

    private val vm by lazy {
        ViewModelProvider(this).get(PaymentMethodBsVM::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.bs_payment_method_bottom_sheet, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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