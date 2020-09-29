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

class DeliveryBottomSheetAdapter(
    private val context: Context,
    private val deliveries: List<Delivery>
) :
    RecyclerView.Adapter<DeliveryBottomSheetViewHolder>() {

    private lateinit var deliveryBottomSheetViewHolder: DeliveryBottomSheetViewHolder
    private val activity = context as AppCompatActivity

    private val _onClicked = MutableLiveData<Delivery>()
    val onClicked: LiveData<Delivery> = _onClicked

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeliveryBottomSheetViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_delivery, parent, false)
        deliveryBottomSheetViewHolder = DeliveryBottomSheetViewHolder(view)
        setupObserver()
        return deliveryBottomSheetViewHolder
    }

    override fun onBindViewHolder(holder: DeliveryBottomSheetViewHolder, position: Int) {
        holder.bind(deliveries[position])
    }

    override fun getItemCount(): Int = deliveries.size

    private fun setupObserver() {
        deliveryBottomSheetViewHolder.onClicked.observe(activity, Observer { delivery ->
            _onClicked.value = delivery
        })
    }
}