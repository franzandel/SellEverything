package com.franzandel.selleverything.features.cart.presentation

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.data.entity.Product
import com.franzandel.selleverything.extension.hide
import kotlinx.android.synthetic.main.item_cart_header.view.*

/**
 * Created by Franz Andel on 02/10/20.
 * Android Engineer
 */

class CartHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val _onCheckSellerClicked = MutableLiveData<Pair<String, Boolean>>()
    val onCheckSellerClicked: LiveData<Pair<String, Boolean>> = _onCheckSellerClicked

    fun bind(product: Product) {
        itemView.apply {
            if (adapterPosition == 0) vCartHeaderSeparator.hide()
            cbCartHeaderCheck.isChecked = product.isChecked
            tvCartHeaderSeller.text = product.seller
            tvCartHeaderLocation.text = product.location

//            cbCartHeaderCheck.setOnCheckedChangeListener { _, isChecked ->
//                _onCheckSellerClicked.value = Pair(product.seller, isChecked)
//            }
            cbCartHeaderCheck.setOnClickListener {
                _onCheckSellerClicked.value = Pair(product.seller, cbCartHeaderCheck.isChecked)
            }
        }
    }
}