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

fun String.removeSpecialCharacter(): String =
    this.replace("[^0-9a-zA-Z]+".toRegex(), "")

fun String.removeText(): String =
    this.replace("[a-zA-Z]".toRegex(), "")

fun String.removeWellFormattedPrice(): String =
    this.removeText().removeSpecialCharacter()