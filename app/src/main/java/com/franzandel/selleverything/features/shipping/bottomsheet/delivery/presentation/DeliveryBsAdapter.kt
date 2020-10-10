package com.franzandel.selleverything.features.shipping.bottomsheet.delivery.presentation

import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.franzandel.selleverything.R
import com.franzandel.selleverything.base.presentation.BaseAdapter
import com.franzandel.selleverything.features.shipping.bottomsheet.delivery.data.entity.Delivery

/**
 * Created by Franz Andel on 27/09/20.
 * Android Engineer
 */

class DeliveryBsAdapter(
    context: Context,
    private val deliveries: List<Delivery>
) : BaseAdapter<DeliveryBsViewHolder, Delivery>(context) {

    private val activity = context as AppCompatActivity

    private val _onClicked = MutableLiveData<Delivery>()
    val onClicked: LiveData<Delivery> = _onClicked

    override fun getItemLayoutId(): Int = R.layout.item_delivery

    override fun getViewHolder(view: View): DeliveryBsViewHolder = DeliveryBsViewHolder(view)

    override fun getCurrentList(): List<Delivery> = deliveries

    override fun actionOnCreateViewHolder() {
        setupObserver()
    }

    override fun onBindViewHolder(holder: DeliveryBsViewHolder, position: Int) {
        holder.bind(deliveries[position])
    }

    private fun setupObserver() {
        viewHolder.onClicked.observe(activity, Observer { delivery ->
            _onClicked.value = delivery
        })
    }
}