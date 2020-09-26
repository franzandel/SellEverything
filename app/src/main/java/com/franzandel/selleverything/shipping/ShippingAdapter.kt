package com.franzandel.selleverything.shipping

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.R
import com.franzandel.selleverything.data.entity.MultiType
import com.franzandel.selleverything.data.enums.ShippingSection
import com.franzandel.selleverything.newest.Product

/**
 * Created by Franz Andel on 26/09/20.
 * Android Engineer
 */

class ShippingAdapter(private val context: Context) :
    ListAdapter<MultiType<Product>, RecyclerView.ViewHolder>(ShippingDiffCallback()) {

    companion object {
        private const val ADDRESS_TYPE = 0
        private const val HEADER_TYPE = 1
        private const val CONTENT_TYPE = 2
        private const val FOOTER_TYPE = 3
        private const val SUMMARY_TYPE = 4
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position].section) {
            ShippingSection.ADDRESS -> ADDRESS_TYPE
            ShippingSection.HEADER -> HEADER_TYPE
            ShippingSection.CONTENT -> CONTENT_TYPE
            ShippingSection.FOOTER -> FOOTER_TYPE
            ShippingSection.SUMMARY -> SUMMARY_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        return when (viewType) {
            ADDRESS_TYPE -> {
                val view = layoutInflater.inflate(R.layout.item_shipping_address, parent, false)
                ShippingAddressViewHolder(view)
            }
            HEADER_TYPE -> {
                val view = layoutInflater.inflate(R.layout.item_shipping_header, parent, false)
                ShippingHeaderViewHolder(view)
            }
            CONTENT_TYPE -> {
                val view = layoutInflater.inflate(R.layout.item_shipping_content, parent, false)
                ShippingContentViewHolder(view)
            }
            FOOTER_TYPE -> {
                val view = layoutInflater.inflate(R.layout.item_shipping_footer, parent, false)
                ShippingFooterViewHolder(view)
            }
            else -> {
                val view = layoutInflater.inflate(R.layout.item_shipping_summary, parent, false)
                ShippingSummaryViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val productMultiType = currentList[position]
        when (productMultiType.section) {
            ShippingSection.ADDRESS -> (holder as ShippingAddressViewHolder).bind(productMultiType.data)
            ShippingSection.HEADER -> (holder as ShippingHeaderViewHolder).bind(productMultiType.data)
            ShippingSection.CONTENT -> (holder as ShippingContentViewHolder).bind(productMultiType.data)
            ShippingSection.FOOTER -> (holder as ShippingFooterViewHolder).bind(productMultiType.data)
            ShippingSection.SUMMARY -> (holder as ShippingSummaryViewHolder).bind(productMultiType.data)
        }
    }

    override fun getItemCount(): Int = currentList.size
}