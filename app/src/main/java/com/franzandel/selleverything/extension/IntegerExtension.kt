package com.franzandel.selleverything.extension

/**
 * Created by Franz Andel on 21/08/20.
 * Android Engineer
 */

fun Int.getFormattedWeight(): String {
    val weightMod = this / 1000
    return if (weightMod > 0) {
        "$weightMod Kg"
    } else {
        "$this Gram"
    }
}