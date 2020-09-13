package com.franzandel.selleverything.extension

/**
 * Created by Franz Andel on 21/08/20.
 * Android Engineer
 */

fun String.addPercentage() =
    "$this%"

fun String.addQtyLeft() =
    "Sisa $this"

fun String.getDiscountedPrice(discountPercent: String): Double {
    val price = this.toDouble()
    val discountPercentage = discountPercent.toDouble() / 100
    val discountPrice = price * discountPercentage
    return price - discountPrice
}