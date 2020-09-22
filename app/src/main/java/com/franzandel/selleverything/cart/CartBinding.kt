package com.franzandel.selleverything.cart

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.franzandel.selleverything.BR

/**
 * Created by Franz Andel on 21/09/20.
 * Android Engineer
 */

class CartBinding : BaseObservable() {

    private var qty = "1"

    private val _onQtyChanged = MutableLiveData<String>()
    val onQtyChanged: LiveData<String> = _onQtyChanged

    @Bindable
    fun getQty(): String = qty

    fun setQty(qty: String) {
        onQtyChanged(qty)
    }

    private fun onQtyChanged(qty: String) {
        this.qty = if (qty.isEmpty()) {
            "0"
        } else {
            handleQtyIfNotEmpty(qty)
        }
        notifyPropertyChanged(BR.qty)
        notifyPropertyChanged(BR.qtyZeroValidation)
        _onQtyChanged.value = this.qty
    }

    private fun handleQtyIfNotEmpty(qty: String): String =
        if (qty.length > 1 && qty.startsWith("0")) {
            qty.substring(1)
        } else {
            qty
        }

    @Bindable
    fun getQtyZeroValidation(): Int =
        if (qty == "0") {
            View.VISIBLE
        } else {
            View.GONE
        }
}