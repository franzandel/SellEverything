package com.franzandel.selleverything.newest

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewProduct(
    val products: List<Product>
)