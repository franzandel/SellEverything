package com.franzandel.selleverything.features.shipping.data.entity

/**
 * Created by Franz Andel on 29/09/20.
 * Android Engineer
 */

data class ShippingFooter(
    val totalProductsPrice: String,
    var shippingPrice: String = "0",
    var seller: String,
    var adapterPosition: Int = -1,
    var deliveryType: String = "",
    val totalShipping: Int
)