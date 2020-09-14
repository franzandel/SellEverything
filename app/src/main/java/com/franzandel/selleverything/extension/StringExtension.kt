package com.franzandel.selleverything.extension

/**
 * Created by Franz Andel on 21/08/20.
 * Android Engineer
 */

fun String.addCashbackPercentage() =
    "Cashback $this%"

fun String.addPercentage() =
    "$this%"

fun String.addQtyLeft() =
    "Sisa $this"

fun String.addUnit() =
    "$this Buah"

fun String.getDiscountedPrice(discountPercent: String): Double {
    if (this.isEmpty() or discountPercent.isEmpty()) return 0.0

    val price = this.toDouble()
    val discountPercentage = discountPercent.toDouble() / 100
    val discountPrice = price * discountPercentage
    return price - discountPrice
}