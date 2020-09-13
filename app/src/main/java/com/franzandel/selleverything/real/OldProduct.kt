package com.franzandel.selleverything.real

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OldProduct(
    val resources: List<Resource>
)