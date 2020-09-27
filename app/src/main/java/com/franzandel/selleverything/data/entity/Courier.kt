package com.franzandel.selleverything.data.entity

/**
 * Created by Franz Andel on 27/09/20.
 * Android Engineer
 */

data class Courier(
    val name: String,
    val price: String,
    var isChecked: Boolean = false
)