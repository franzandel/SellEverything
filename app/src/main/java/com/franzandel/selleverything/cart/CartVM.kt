package com.franzandel.selleverything.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.franzandel.selleverything.data.database.AppDatabase
import com.franzandel.selleverything.extension.getDiscountedPrice
import com.franzandel.selleverything.extension.getFormattedIDNPrice
import com.franzandel.selleverything.extension.removeSpecialCharacter
import com.franzandel.selleverything.extension.removeText
import com.franzandel.selleverything.newest.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Franz Andel on 16/09/20.
 * Android Engineer
 */

class CartVM(application: Application) : AndroidViewModel(application) {
    private val cartProductDao = AppDatabase.invoke(application.applicationContext).cartProductDao()
    private val cartRepository: CartRepository = CartRepositoryImpl(cartProductDao)
    val cartProducts: LiveData<List<Product>> = cartRepository.cartProducts

    fun getTotalCheckedProductsCount(products: List<Product>): String = products.count { product ->
        product.isChecked
    }.toString()

    fun getTotalCheckedProductsPrice(products: List<Product>): String =
        products.filter { product ->
            product.isChecked
        }.sumByDouble { product ->
            product.price.getDiscountedPrice(product.discountPercentage) * product.currentQty
        }.toLong().getFormattedIDNPrice()

    fun getTotalProductsPriceAfterMinusClicked(
        product: Product,
        currentTotalPrice: String
    ): String {
        val existingTotalPrice = currentTotalPrice.removeSpecialCharacter().removeText().toLong()
        val productPrice = product.price.getDiscountedPrice(product.discountPercentage).toLong()
        val lastTotalPrice = existingTotalPrice - productPrice
        return lastTotalPrice.getFormattedIDNPrice()
    }

    fun getTotalProductsPriceAfterPlusClicked(product: Product, currentTotalPrice: String): String {
        val existingTotalPrice = currentTotalPrice.removeSpecialCharacter().removeText().toLong()
        val productPrice = product.price.getDiscountedPrice(product.discountPercentage).toLong()
        val lastTotalPrice = existingTotalPrice + productPrice
        return lastTotalPrice.getFormattedIDNPrice()
    }

    fun getTotalCheckedProductsCountAfterMinusClicked(currentCheckedProductsCount: String): String {
        val existingProductsCount =
            currentCheckedProductsCount.removeSpecialCharacter().removeText()
        return (existingProductsCount.toInt() - 1).toString()
    }

    fun getTotalCheckedProductsCountAfterPlusClicked(currentCheckedProductsCount: String): String {
        val existingProductsCount =
            currentCheckedProductsCount.removeSpecialCharacter().removeText()
        return (existingProductsCount.toInt() + 1).toString()
    }

    fun updateCart(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.updateCart(product)
        }
    }

    fun getTotalCheckedProductsQty(products: List<Product>): String =
        products.filter { product ->
            product.isChecked
        }.sumBy { product ->
            product.currentQty
        }.toString()

    fun deleteAllFromCart(products: List<Product>) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteAllFromCart(products)
        }
    }

    fun deleteFromCart(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteFromCart(product)
        }
    }
}