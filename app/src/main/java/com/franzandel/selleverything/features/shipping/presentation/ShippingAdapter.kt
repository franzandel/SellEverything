package com.franzandel.selleverything.features.shipping.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.R
import com.franzandel.selleverything.features.shipping.data.entity.ShippingMultiType
import com.franzandel.selleverything.features.shipping.data.enums.ShippingSection

/**
 * Created by Franz Andel on 26/09/20.
 * Android Engineer
 */

class ShippingAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

    fun setData(currentList: List<ShippingMultiType<Any>>) {
        this.currentList = currentList
    }

    private val activity = context as AppCompatActivity
    private lateinit var shippingFooterViewHolder: ShippingFooterViewHolder
    private lateinit var shippingSummaryViewHolder: ShippingSummaryViewHolder

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
                shippingFooterViewHolder = ShippingFooterViewHolder(view)
                setupFooterObserver()
                shippingFooterViewHolder
            }
            else -> {
                val view = layoutInflater.inflate(R.layout.item_shipping_summary, parent, false)
                shippingSummaryViewHolder = ShippingSummaryViewHolder(view)
                setupSummaryObserver()
                shippingSummaryViewHolder
            }
        }
    }

    private fun setupFooterObserver() {
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
        shippingSummaryViewHolder.onShippingChoosePaymentClicked.observe(
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

    override fun getItemCount(): Int = currentList.size
}