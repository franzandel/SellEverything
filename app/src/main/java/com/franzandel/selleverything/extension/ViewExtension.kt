package com.franzandel.selleverything.extension

import android.view.View

/**
 * Created by Franz Andel on 21/08/20.
 * Android Engineer
 */

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}