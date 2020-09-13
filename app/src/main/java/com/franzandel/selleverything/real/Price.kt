package com.franzandel.selleverything.real

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Price(
    val currency: String,
    val value: String
)