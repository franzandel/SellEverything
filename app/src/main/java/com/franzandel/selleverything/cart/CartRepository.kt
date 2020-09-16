package com.franzandel.selleverything.cart

import androidx.lifecycle.LiveData
import com.franzandel.selleverything.newest.Product

/**
 * Created by Franz Andel on 16/09/20.
 * Android Engineer
 */

interface CartRepository {
    val cartProducts: LiveData<List<Product>>
}