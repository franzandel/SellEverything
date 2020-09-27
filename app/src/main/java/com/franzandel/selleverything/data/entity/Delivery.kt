package com.franzandel.selleverything.data.entity

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