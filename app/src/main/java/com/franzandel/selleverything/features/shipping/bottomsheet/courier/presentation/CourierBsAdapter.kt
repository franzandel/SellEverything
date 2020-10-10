package com.franzandel.selleverything.features.shipping.bottomsheet.courier.presentation

import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.franzandel.selleverything.R
import com.franzandel.selleverything.base.presentation.BaseAdapter
import com.franzandel.selleverything.features.shipping.bottomsheet.data.entity.Courier

/**
 * Created by Franz Andel on 27/09/20.
 * Android Engineer
 */

class CourierBsAdapter(context: Context, private val couriers: List<Courier>) :
    BaseAdapter<CourierBsViewHolder, Courier>(context) {

    private val activity = context as AppCompatActivity

    private val _onClicked = MutableLiveData<Courier>()
    val onClicked: LiveData<Courier> = _onClicked

    override fun getItemLayoutId(): Int = R.layout.item_courier

    override fun getViewHolder(view: View): CourierBsViewHolder = CourierBsViewHolder(view)

    override fun getCurrentList(): List<Courier> = couriers

    override fun actionOnCreateViewHolder() {
        setupObserver()
    }

    override fun onBindViewHolder(holder: CourierBsViewHolder, position: Int) {
        holder.bind(couriers[position])
    }

    private fun setupObserver() {
        viewHolder.onClicked.observe(activity, Observer { courier ->
            _onClicked.value = courier
        })
    }
}