package com.franzandel.selleverything.detail

import com.franzandel.selleverything.newest.Product

/**
 * Created by Franz Andel on 15/09/20.
 * Android Engineer
 */

interface DetailRepository {
    suspend fun insertToCart(product: Product)
}