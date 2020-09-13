package com.franzandel.selleverything.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.franzandel.selleverything.R
import com.franzandel.selleverything.newest.Product

/**
 * Created by Franz Andel on 17/08/20.
 * Android Engineer
 */

class SellEverythingAdapter(private val context: Context) :
    ListAdapter<Product, SellEverythingViewHolder>(SellEverythingDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellEverythingViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_sell_everything, parent, false)
        return SellEverythingViewHolder(view)
    }

    override fun onBindViewHolder(holder: SellEverythingViewHolder, position: Int) {
        val product = currentList[position]
        holder.bind(product)
    }
}