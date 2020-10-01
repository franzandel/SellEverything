package com.franzandel.selleverything.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Franz Andel on 28/09/20.
 * Android Engineer
 */

@Parcelize
data class ShippingSummary(
    val totalQty: String,
    val totalPrice: String,
    val totalShippingPrice: String = "0"
) : Parcelable