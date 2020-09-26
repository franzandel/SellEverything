package com.franzandel.selleverything.shipping

import androidx.recyclerview.widget.DiffUtil
import com.franzandel.selleverything.data.entity.MultiType
import com.franzandel.selleverything.newest.Product

/**
 * Created by Franz Andel on 26/09/20.
 * Android Engineer
 */

class ShippingDiffCallback : DiffUtil.ItemCallback<MultiType<Product>>() {

    override fun areItemsTheSame(
        oldItem: MultiType<Product>,
        newItem: MultiType<Product>
    ): Boolean =
        oldItem.data?.id == newItem.data?.id

    override fun areContentsTheSame(
        oldItem: MultiType<Product>,
        newItem: MultiType<Product>
    ): Boolean =
        oldItem.data == newItem.data
}