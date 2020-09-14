package com.franzandel.selleverything.newest

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Product(
    val id: String,
    val cashback: String,
    @Json(name = "discount_percentage")
    val discountPercentage: String,
    @Json(name = "image_name")
    val imageName: String,
    val location: String,
    val price: String,
    val quantity: String,
    val title: String,
    val weight: String,
    val condition: String,
    @Json(name = "min_order")
    val minOrder: String,
    val category: String,
    val description: String
) : Parcelable