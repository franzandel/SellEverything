package com.franzandel.selleverything.features.shipping.bottomsheet.courier.presentation

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.extension.getFormattedIDNPrice
import com.franzandel.selleverything.extension.show
import com.franzandel.selleverything.features.shipping.bottomsheet.data.entity.Courier
import kotlinx.android.synthetic.main.item_courier.view.*

/**
 * Created by Franz Andel on 27/09/20.
 * Android Engineer
 */

class CourierBsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val _onClicked = MutableLiveData<Courier>()
    val onClicked: LiveData<Courier> = _onClicked

    fun bind(courier: Courier) {
        itemView.apply {
            tvCourierItemTitle.text = courier.name
            tvCourierItemPrice.text = courier.price.toInt().getFormattedIDNPrice()

            if (courier.isChecked) ivCourierCheck.show()

            setOnClickListener {
                courier.isChecked = true
                ivCourierCheck.show()
                _onClicked.value = courier
            }
        }
    }
}