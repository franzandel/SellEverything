package com.franzandel.selleverything.features.cart.presentation

import androidx.recyclerview.widget.DiffUtil
import com.franzandel.selleverything.data.entity.Product
import com.franzandel.selleverything.features.cart.data.entity.CartMultiType

/**
 * Created by Franz Andel on 17/09/20.
 * Android Engineer
 */

class CartDiffCallback : DiffUtil.ItemCallback<CartMultiType<Product>>() {

    override fun areItemsTheSame(
        oldItem: CartMultiType<Product>,
        newItem: CartMultiType<Product>
    ): Boolean =
        oldItem.data.id == newItem.data.id

    override fun areContentsTheSame(
        oldItem: CartMultiType<Product>,
        newItem: CartMultiType<Product>
    ): Boolean =
        oldItem.data == newItem.data
}