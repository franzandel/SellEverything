package com.franzandel.selleverything.extension

import android.content.Context
import android.content.Intent

/**
 * Created by Franz Andel on 21/08/20.
 * Android Engineer
 */

fun Context.getDrawableIdFromName(imageName: String) =
    resources.getIdentifier(imageName, "drawable", packageName)

fun <T> Context.goTo(clazz: Class<T>, bundle: (Intent.() -> Unit)? = null) {
    Intent(this, clazz).run {
        bundle?.invoke(this)
        startActivity(this)
    }
}