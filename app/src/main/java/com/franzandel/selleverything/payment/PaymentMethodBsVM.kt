package com.franzandel.selleverything.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.franzandel.selleverything.data.entity.PaymentMethod
import com.franzandel.selleverything.data.entity.PaymentMultiType
import com.franzandel.selleverything.data.enums.PaymentSection

/**
 * Created by Franz Andel on 01/10/20.
 * Android Engineer
 */

class PaymentMethodBsVM : ViewModel() {

    private val _paymentMethodsMultiType = MutableLiveData<List<PaymentMultiType<PaymentMethod>>>()
    val paymentMethodsMultiType: LiveData<List<PaymentMultiType<PaymentMethod>>> =
        _paymentMethodsMultiType

    fun processPaymentMethodsMultiType(paymentMethods: List<PaymentMethod>) {
        val paymentsMultiType = mutableListOf<PaymentMultiType<PaymentMethod>>()
        val groupedPaymentMethods = getGroupedByTypePaymentMethod(paymentMethods)

        groupedPaymentMethods.forEach { groupedPaymentMethod ->
            addPaymentMultiTypeHeader(paymentsMultiType, groupedPaymentMethod)
            addPaymentMultiTypeContent(paymentsMultiType, groupedPaymentMethod)
        }

        _paymentMethodsMultiType.value = paymentsMultiType
    }

    private fun getGroupedByTypePaymentMethod(paymentMethods: List<PaymentMethod>): Map<String, List<PaymentMethod>> =
        paymentMethods.groupBy { paymentMethod ->
            paymentMethod.type
        }

    private fun addPaymentMultiTypeHeader(
        paymentsMultiType: MutableList<PaymentMultiType<PaymentMethod>>,
        groupedPaymentMethod: Map.Entry<String, List<PaymentMethod>>
    ) {
        val paymentMethod = PaymentMethod(
            type = groupedPaymentMethod.key
        )
        val paymentMethodHeaderMultiType = PaymentMultiType(
            data = paymentMethod,
            section = PaymentSection.HEADER
        )
        paymentsMultiType.add(paymentMethodHeaderMultiType)
    }

    private fun addPaymentMultiTypeContent(
        paymentsMultiType: MutableList<PaymentMultiType<PaymentMethod>>,
        groupedPaymentMethods: Map.Entry<String, List<PaymentMethod>>
    ) {
        groupedPaymentMethods.value.forEach { paymentMethod ->
            val paymentMethodContentMultiType = PaymentMultiType(
                data = paymentMethod,
                section = PaymentSection.CONTENT
            )
            paymentsMultiType.add(paymentMethodContentMultiType)
        }
    }
}