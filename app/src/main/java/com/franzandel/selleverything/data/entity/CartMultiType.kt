package com.franzandel.selleverything.data.entity

import com.franzandel.selleverything.data.enums.CartSection

/**
 * Created by Franz Andel on 02/10/20.
 * Android Engineer
 */

data class CartMultiType<T>(
    val data: T,
    val section: CartSection
)