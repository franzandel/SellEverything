package com.franzandel.selleverything.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.franzandel.selleverything.R
import com.franzandel.selleverything.newest.Product

/**
 * Created by Franz Andel on 17/09/20.
 * Android Engineer
 */

class CartAdapter(private val context: Context) :
    ListAdapter<Product, CartViewHolder>(CartDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = currentList[position]
        holder.bind(product)
    }

    fun checkAllProducts(isChecked: Boolean) {
        currentList.forEach { product ->
            product.isChecked = isChecked
        }
        notifyDataSetChanged()
    }
}