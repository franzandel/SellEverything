package com.franzandel.selleverything.data.entity

import com.franzandel.selleverything.data.enums.ShippingSection

/**
 * Created by Franz Andel on 26/09/20.
 * Android Engineer
 */

data class ShippingMultiType<T>(
    val data: T? = null,
    val section: ShippingSection
)