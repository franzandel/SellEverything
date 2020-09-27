package com.franzandel.selleverything.courier

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.R
import com.franzandel.selleverything.data.entity.Courier

/**
 * Created by Franz Andel on 27/09/20.
 * Android Engineer
 */

class CourierAdapter(private val context: Context, private val couriers: List<Courier>) :
    RecyclerView.Adapter<CourierViewHolder>() {

    private lateinit var courierViewHolder: CourierViewHolder
    private val activity = context as AppCompatActivity

    private val _onClicked = MutableLiveData<Courier>()
    val onClicked: LiveData<Courier> = _onClicked

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourierViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_courier, parent, false)
        courierViewHolder = CourierViewHolder(view)
        setupObserver()
        return courierViewHolder
    }

    override fun onBindViewHolder(holder: CourierViewHolder, position: Int) {
        holder.bind(couriers[position])
    }

    override fun getItemCount(): Int = couriers.size

    private fun setupObserver() {
        courierViewHolder.onClicked.observe(activity, Observer { courier ->
            _onClicked.value = courier
        })
    }
}