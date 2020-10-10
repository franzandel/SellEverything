package com.franzandel.selleverything.features.shipping.bottomsheet.delivery.data.entity

import com.franzandel.selleverything.features.shipping.bottomsheet.data.entity.Courier

/**
 * Created by Franz Andel on 27/09/20.
 * Android Engineer
 */

data class Delivery(
    val type: String,
    val minPrice: String,
    val maxPrice: String,
    val couriers: List<Courier> = listOf()
)