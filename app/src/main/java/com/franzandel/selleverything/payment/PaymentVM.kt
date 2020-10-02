package com.franzandel.selleverything.payment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.franzandel.selleverything.cart.CartRepository
import com.franzandel.selleverything.cart.CartRepositoryImpl
import com.franzandel.selleverything.data.database.AppDatabase
import com.franzandel.selleverything.data.entity.PaymentMethod
import com.franzandel.selleverything.data.entity.ShippingSummary
import com.franzandel.selleverything.newest.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Franz Andel on 02/10/20.
 * Android Engineer
 */

class PaymentVM(application: Application) : AndroidViewModel(application) {

    private val cartProductDao = AppDatabase.invoke(application.applicationContext).cartProductDao()
    private val cartRepository: CartRepository = CartRepositoryImpl(cartProductDao)

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

    fun deleteAllFromCart() {
        val deleteAllFromCartObserver = Observer<List<Product>> {
            viewModelScope.launch(Dispatchers.IO) {
                cartRepository.deleteAllFromCart(it)
            }
        }

        cartRepository.cartProducts.observeForever(deleteAllFromCartObserver)
        cartRepository.cartProducts.removeObserver(deleteAllFromCartObserver)
    }
}