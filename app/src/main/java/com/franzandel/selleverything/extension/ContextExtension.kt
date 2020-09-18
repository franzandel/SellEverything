package com.franzandel.selleverything.extension

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

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

fun Context.toColor(colorId: Int) = ContextCompat.getColor(this, colorId)

fun Context.showSnackbarWithAction(
    view: View,
    text: String,
    actionText: String,
    duration: Int,
    action: () -> Unit
) {
    Snackbar.make(view, text, duration)
//        .setActionTextColor(this.toColor(R.color.colorSnackbarAction))
        .setAction(actionText) {
            action.invoke()
        }.run {
//            this.view.setBackgroundColor(context.toColor(R.color.colorSnackbarBackground))
            show()
        }
}

fun Context.showToast(message: String) {
    getToast(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(message: String) {
    getToast(this, message, Toast.LENGTH_LONG).show()
}

private fun getToast(context: Context, message: String, duration: Int): Toast {
    return Toast.makeText(context, message, duration)
}