package com.franzandel.selleverything.features.detail.data.repository

import com.franzandel.selleverything.data.entity.Product

/**
 * Created by Franz Andel on 15/09/20.
 * Android Engineer
 */

interface DetailRepository {
    suspend fun insertToCart(product: Product)
}