package com.franzandel.selleverything.payment

import androidx.lifecycle.ViewModel
import com.franzandel.selleverything.data.entity.PaymentMethod
import com.franzandel.selleverything.data.entity.ShippingSummary

/**
 * Created by Franz Andel on 02/10/20.
 * Android Engineer
 */

class PaymentVM : ViewModel() {

    fun getTotalPaymentPriceWithoutServicePrice(shippingSummary: ShippingSummary): Long {
        val totalPrice = shippingSummary.totalPrice.toLong()
        val totalShippingPrice = shippingSummary.totalShippingPrice.toLong()
        return totalPrice + totalShippingPrice
    }

    fun getTotalPaymentPrice(shippingSummary: ShippingSummary, paymentMethod: PaymentMethod): Long {
        val totalPaymentPriceWithoutServicePrice =
            getTotalPaymentPriceWithoutServicePrice(shippingSummary)
        val servicePrice = paymentMethod.servicePrice
        return totalPaymentPriceWithoutServicePrice + servicePrice
    }
}