package com.franzandel.selleverything.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.franzandel.selleverything.data.database.AppDatabase
import com.franzandel.selleverything.extension.getDiscountedPrice
import com.franzandel.selleverything.extension.getFormattedIDNPrice
import com.franzandel.selleverything.newest.Product

/**
 * Created by Franz Andel on 16/09/20.
 * Android Engineer
 */

class CartVM(application: Application) : AndroidViewModel(application) {
    private val cartProductDao = AppDatabase.invoke(application.applicationContext).cartProductDao()
    private val cartRepository: CartRepository = CartRepositoryImpl(cartProductDao)
    val cartProducts: LiveData<List<Product>> = cartRepository.cartProducts

    fun getTotalProductsPrice(products: List<Product>): String =
        products.sumByDouble { product ->
            product.price.getDiscountedPrice(product.discountPercentage)
        }.toLong().getFormattedIDNPrice()
}