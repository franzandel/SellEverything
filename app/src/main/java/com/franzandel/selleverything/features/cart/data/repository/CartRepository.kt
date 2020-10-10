package com.franzandel.selleverything.features.cart.data.repository

import androidx.lifecycle.LiveData
import com.franzandel.selleverything.data.entity.Product

/**
 * Created by Franz Andel on 16/09/20.
 * Android Engineer
 */

interface CartRepository {
    val cartProducts: LiveData<List<Product>>
    suspend fun updateCart(product: Product)
    suspend fun deleteFromCart(product: Product)
    suspend fun deleteAllFromCart(products: List<Product>)
}