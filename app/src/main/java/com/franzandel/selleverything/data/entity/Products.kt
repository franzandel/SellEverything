package com.franzandel.selleverything.data.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Products(
    val products: List<Product>
)