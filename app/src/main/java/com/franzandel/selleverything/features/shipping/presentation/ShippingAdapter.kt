package com.franzandel.selleverything.features.shipping.presentation

import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.R
import com.franzandel.selleverything.base.BaseAdapter
import com.franzandel.selleverything.features.shipping.data.entity.ShippingMultiType
import com.franzandel.selleverything.features.shipping.data.enums.ShippingSection

/**
 * Created by Franz Andel on 26/09/20.
 * Android Engineer
 */

class ShippingAdapter(context: Context) :
    BaseAdapter<RecyclerView.ViewHolder, ShippingMultiType<Any>>(context) {

    companion object {
        private const val ADDRESS_TYPE = 0
        private const val HEADER_TYPE = 1
        private const val CONTENT_TYPE = 2
        private const val FOOTER_TYPE = 3
        private const val SUMMARY_TYPE = 4
    }

    private val _onDeliveryOrCourierBsSelected =
        MutableLiveData<Pair<List<ShippingMultiType<Any>>, String>>()
    val onDeliveryOrCourierBsSelected: LiveData<Pair<List<ShippingMultiType<Any>>, String>> =
        _onDeliveryOrCourierBsSelected

    private val _onDeliveryOrCourierBsOpened =
        MutableLiveData<Pair<List<ShippingMultiType<Any>>, String>>()
    val onDeliveryOrCourierBsOpened: LiveData<Pair<List<ShippingMultiType<Any>>, String>> =
        _onDeliveryOrCourierBsOpened

    private val _onShippingChoosePaymentClicked = MutableLiveData<List<ShippingMultiType<Any>>>()
    val onShippingChoosePaymentClicked: LiveData<List<ShippingMultiType<Any>>> =
        _onShippingChoosePaymentClicked

    private var currentList = listOf<ShippingMultiType<Any>>()
    private val activity = context as AppCompatActivity

    override fun getItemLayoutId(): Int =
        when (viewType) {
            ADDRESS_TYPE -> R.layout.item_shipping_address
            HEADER_TYPE -> R.layout.item_shipping_header
            CONTENT_TYPE -> R.layout.item_shipping_content
            FOOTER_TYPE -> R.layout.item_shipping_footer
            else -> R.layout.item_shipping_summary
        }

    override fun getViewHolder(view: View): RecyclerView.ViewHolder =
        when (viewType) {
            ADDRESS_TYPE -> ShippingAddressViewHolder(view)
            HEADER_TYPE -> ShippingHeaderViewHolder(view)
            CONTENT_TYPE -> ShippingContentViewHolder(view)
            FOOTER_TYPE -> ShippingFooterViewHolder(view)
            else -> ShippingSummaryViewHolder(view)
        }

    override fun getCurrentList(): List<ShippingMultiType<Any>> = currentList

    fun setData(currentList: List<ShippingMultiType<Any>>) {
        this.currentList = currentList
    }

    override fun getItemViewType(position: Int): Int =
        when (currentList[position].section) {
            ShippingSection.ADDRESS -> ADDRESS_TYPE
            ShippingSection.HEADER -> HEADER_TYPE
            ShippingSection.CONTENT -> CONTENT_TYPE
            ShippingSection.FOOTER -> FOOTER_TYPE
            ShippingSection.SUMMARY -> SUMMARY_TYPE
        }

    override fun actionOnCreateViewHolder() {
        when (viewType) {
            FOOTER_TYPE -> setupFooterObserver()
            SUMMARY_TYPE -> setupSummaryObserver()
        }
    }

    private fun setupFooterObserver() {
        val shippingFooterViewHolder = (viewHolder as ShippingFooterViewHolder)
        shippingFooterViewHolder.onDeliveryOrCourierBsSelected.observe(
            activity,
            Observer { courierPrice ->
                _onDeliveryOrCourierBsSelected.value = Pair(currentList, courierPrice)
            })

        shippingFooterViewHolder.onDeliveryOrCourierBsOpened.observe(
            activity,
            Observer { courierPrice ->
                _onDeliveryOrCourierBsOpened.value = Pair(currentList, courierPrice)
            })
    }

    private fun setupSummaryObserver() {
        (viewHolder as ShippingSummaryViewHolder).onShippingChoosePaymentClicked.observe(
            activity,
            Observer {
                _onShippingChoosePaymentClicked.value = currentList
            })
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
}