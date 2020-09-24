package com.franzandel.selleverything.extension

import android.view.View
import android.view.ViewGroup

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

fun View.setMargin(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
    val params = (layoutParams as? ViewGroup.MarginLayoutParams)
    params?.leftMargin = left ?: params?.leftMargin
    params?.topMargin = top ?: params?.topMargin
    params?.rightMargin = right ?: params?.rightMargin
    params?.bottomMargin = bottom ?: params?.bottomMargin
}