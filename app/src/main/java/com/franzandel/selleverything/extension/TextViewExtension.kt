package com.franzandel.selleverything.extension

import android.graphics.Paint
import android.widget.TextView

/**
 * Created by Franz Andel on 21/08/20.
 * Android Engineer
 */

fun TextView.showStrikeThrough(show: Boolean = true) {
    paintFlags =
        if (show) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
}