package com.franzandel.selleverything.delivery

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.R
import com.franzandel.selleverything.data.entity.Delivery

/**
 * Created by Franz Andel on 27/09/20.
 * Android Engineer
 */

class DeliveryAdapter(private val context: Context, private val deliveries: List<Delivery>) :
    RecyclerView.Adapter<DeliveryViewHolder>() {

    private lateinit var deliveryViewHolder: DeliveryViewHolder
    private val activity = context as AppCompatActivity

    private val _onClicked = MutableLiveData<Delivery>()
    val onClicked: LiveData<Delivery> = _onClicked

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_delivery, parent, false)
        deliveryViewHolder = DeliveryViewHolder(view)
        setupObserver()
        return deliveryViewHolder
    }

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(deliveries[position])
    }

    override fun getItemCount(): Int = deliveries.size

    private fun setupObserver() {
        deliveryViewHolder.onClicked.observe(activity, Observer { delivery ->
            _onClicked.value = delivery
        })
    }
}