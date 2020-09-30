package com.franzandel.selleverything.data.entity

/**
 * Created by Franz Andel on 29/09/20.
 * Android Engineer
 */

data class ShippingFooter(
    val totalProductsPrice: String,
    // TODO: CHANGE SHIPPING PRICE LOGIC WITH THIS VARIABLE
    val shippingPrice: String = "0",
    var adapterPosition: Int = -1,
    var deliveryType: String = "",
    val totalShipping: Int
)