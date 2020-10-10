package com.franzandel.selleverything.features.payment.data.entity

import androidx.annotation.DrawableRes

/**
 * Created by Franz Andel on 01/10/20.
 * Android Engineer
 */

data class PaymentMethod(
    val type: String,
    val name: String = "",
    val servicePrice: Int = 0,
    @DrawableRes val icon: Int = 0
)