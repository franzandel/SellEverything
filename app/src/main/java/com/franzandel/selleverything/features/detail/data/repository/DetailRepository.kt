package com.franzandel.selleverything.features.detail.data.repository

import androidx.lifecycle.LiveData
import com.franzandel.selleverything.data.entity.Product

/**
 * Created by Franz Andel on 15/09/20.
 * Android Engineer
 */

interface DetailRepository {
    val cartProducts: LiveData<List<Product>>
    suspend fun insertToCart(product: Product)
}