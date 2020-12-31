package com.franzandel.selleverything.features.home.data

import androidx.lifecycle.LiveData
import com.franzandel.selleverything.data.entity.Product

/**
 * Created by Franz Andel on 31/12/20.
 * Android Engineer
 */

interface HomeRepository {
    fun getProducts(): LiveData<List<Product>>
}