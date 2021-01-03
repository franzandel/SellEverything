package com.franzandel.selleverything.features.home.data

import com.franzandel.selleverything.data.entity.Product
import com.franzandel.selleverything.external.Result

/**
 * Created by Franz Andel on 31/12/20.
 * Android Engineer
 */

interface HomeRepository {
    suspend fun getProducts(): Result<List<Product>>
}