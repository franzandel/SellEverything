package com.franzandel.selleverything.extension

import java.text.NumberFormat
import java.util.*

/**
 * Created by Franz Andel on 18/09/20.
 * Android Engineer
 */

fun Number.getFormattedIDNPrice(): String {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    numberFormat.maximumFractionDigits = 0
    numberFormat.currency = Currency.getInstance("IDR")
    return numberFormat.format(this)
}