package com.franzandel.selleverything.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.franzandel.selleverything.newest.Product

/**
 * Created by Franz Andel on 17/08/20.
 * Android Engineer
 */

class SellEverythingDiffCallback : DiffUtil.ItemCallback<Product>() {

    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
        oldItem == newItem
}