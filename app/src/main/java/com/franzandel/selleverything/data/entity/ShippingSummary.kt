package com.franzandel.selleverything.data.entity

/**
 * Created by Franz Andel on 28/09/20.
 * Android Engineer
 */

data class ShippingSummary(
    val totalQty: String,
    val totalPrice: String,
    val totalOrderPrice: String = "0"
)