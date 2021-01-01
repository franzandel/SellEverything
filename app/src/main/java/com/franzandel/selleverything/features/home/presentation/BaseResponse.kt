package com.franzandel.selleverything.features.home.presentation

import com.franzandel.selleverything.data.entity.Product
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseResponse(
    val result: List<Product>,
    val timeIn: String
)