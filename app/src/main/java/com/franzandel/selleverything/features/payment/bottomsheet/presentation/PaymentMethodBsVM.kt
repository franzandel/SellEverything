package com.franzandel.selleverything.features.payment.bottomsheet.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.franzandel.selleverything.features.payment.bottomsheet.data.entity.PaymentMethodMultiType
import com.franzandel.selleverything.features.payment.bottomsheet.data.enums.PaymentMethodSection
import com.franzandel.selleverything.features.payment.data.entity.PaymentMethod

/**
 * Created by Franz Andel on 01/10/20.
 * Android Engineer
 */

class PaymentMethodBsVM : ViewModel() {

    private val _paymentMethodsMultiType =
        MutableLiveData<List<PaymentMethodMultiType<PaymentMethod>>>()
    val paymentMethodsMultiType: LiveData<List<PaymentMethodMultiType<PaymentMethod>>> =
        _paymentMethodsMultiType

    fun processPaymentMethodsMultiType(paymentMethods: List<PaymentMethod>) {
        val paymentsMultiType = mutableListOf<PaymentMethodMultiType<PaymentMethod>>()
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
        paymentsMultiType: MutableList<PaymentMethodMultiType<PaymentMethod>>,
        groupedPaymentMethod: Map.Entry<String, List<PaymentMethod>>
    ) {
        val paymentMethod = PaymentMethod(
            type = groupedPaymentMethod.key
        )
        val paymentMethodHeaderMultiType = PaymentMethodMultiType(
            data = paymentMethod,
            section = PaymentMethodSection.HEADER
        )
        paymentsMultiType.add(paymentMethodHeaderMultiType)
    }

    private fun addPaymentMultiTypeContent(
        paymentsMultiType: MutableList<PaymentMethodMultiType<PaymentMethod>>,
        groupedPaymentMethods: Map.Entry<String, List<PaymentMethod>>
    ) {
        groupedPaymentMethods.value.forEach { paymentMethod ->
            val paymentMethodContentMultiType = PaymentMethodMultiType(
                data = paymentMethod,
                section = PaymentMethodSection.CONTENT
            )
            paymentsMultiType.add(paymentMethodContentMultiType)
        }
    }
}