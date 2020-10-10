package com.franzandel.selleverything.features.shipping.data.entity

import com.franzandel.selleverything.features.shipping.data.enums.ShippingSection

/**
 * Created by Franz Andel on 26/09/20.
 * Android Engineer
 */

data class ShippingMultiType<T>(
    val data: T? = null,
    val section: ShippingSection
)