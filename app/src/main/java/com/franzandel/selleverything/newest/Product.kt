package com.franzandel.selleverything.newest

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    val id: String,
    @Json(name = "discount_percentage")
    val discountPercentage: String,
    @Json(name = "image_name")
    val imageName: String,
    val location: String,
    val price: String,
    val quantity: String,
    val title: String
)