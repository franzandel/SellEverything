package com.franzandel.selleverything.courier

import androidx.lifecycle.ViewModel
import com.franzandel.selleverything.data.entity.Courier

/**
 * Created by Franz Andel on 27/09/20.
 * Android Engineer
 */

class CourierVM : ViewModel() {

    fun resetCouriersIsChecked(couriers: List<Courier>, currentCourier: Courier) {
        couriers.forEach { courier ->
            courier.isChecked = false
        }
        currentCourier.isChecked = true
    }
}