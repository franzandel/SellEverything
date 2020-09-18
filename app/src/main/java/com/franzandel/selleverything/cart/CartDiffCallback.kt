package com.franzandel.selleverything.cart

import androidx.recyclerview.widget.DiffUtil
import com.franzandel.selleverything.newest.Product

/**
 * Created by Franz Andel on 17/09/20.
 * Android Engineer
 */

class CartDiffCallback : DiffUtil.ItemCallback<Product>() {

    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
        oldItem == newItem
}