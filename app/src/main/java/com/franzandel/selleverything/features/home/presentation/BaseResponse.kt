package com.franzandel.selleverything.features.home.presentation

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseResponse<T>(
    val result: T,
    val timeIn: String
)