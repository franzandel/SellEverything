package com.franzandel.selleverything.shipping

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.R
import com.franzandel.selleverything.data.entity.MultiType
import com.franzandel.selleverything.data.enums.AdapterSection
import com.franzandel.selleverything.newest.Product

/**
 * Created by Franz Andel on 26/09/20.
 * Android Engineer
 */

class ShippingAdapter(private val context: Context) :
    ListAdapter<MultiType<Product>, RecyclerView.ViewHolder>(ShippingDiffCallback()) {

    companion object {
        private const val HEADER_TYPE = 0
        private const val CONTENT_TYPE = 1
        private const val FOOTER_TYPE = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position].section) {
            AdapterSection.HEADER -> HEADER_TYPE
            AdapterSection.CONTENT -> CONTENT_TYPE
            AdapterSection.FOOTER -> FOOTER_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        return when (viewType) {
            HEADER_TYPE -> {
                val view = layoutInflater.inflate(R.layout.item_shipping_header, parent, false)
                ShippingHeaderViewHolder(view)
            }
            CONTENT_TYPE -> {
                val view = layoutInflater.inflate(R.layout.item_shipping_content, parent, false)
                ShippingContentViewHolder(view)
            }
            else -> {
                val view = layoutInflater.inflate(R.layout.item_shipping_footer, parent, false)
                ShippingFooterViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val productMultiType = currentList[position]
        when (productMultiType.section) {
            AdapterSection.HEADER -> (holder as ShippingHeaderViewHolder).bind(productMultiType.data)
            AdapterSection.CONTENT -> (holder as ShippingContentViewHolder).bind(productMultiType.data)
            AdapterSection.FOOTER -> (holder as ShippingFooterViewHolder).bind(productMultiType.data)
        }
    }

    override fun getItemCount(): Int = currentList.size
}