package com.franzandel.selleverything.extension

import java.text.NumberFormat
import java.util.*

/**
 * Created by Franz Andel on 21/08/20.
 * Android Engineer
 */

fun Int.getFormattedIDNPrice(): String {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    numberFormat.maximumFractionDigits = 0
    numberFormat.currency = Currency.getInstance("IDR")
    return numberFormat.format(this)
}

fun Int.getFormattedWeight(): String {
    val weightMod = this / 1000
    return if (weightMod > 0) {
        "$weightMod Kg"
    } else {
        "$this Gram"
    }
}