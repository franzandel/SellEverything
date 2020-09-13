package com.franzandel.selleverything.real

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Resource(
    val availability: String,
    val availabilityDate: String,
    val brand: String,
    val color: String,
    val condition: String,
    val description: String,
    val gender: String,
    val id: String,
    val imageLink: String,
    val link: String,
    val price: Price,
    val sizes: List<String>,
    val title: String
)