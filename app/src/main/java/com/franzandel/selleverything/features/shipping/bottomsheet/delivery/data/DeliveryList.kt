package com.franzandel.selleverything.features.shipping.bottomsheet.delivery.data

import com.franzandel.selleverything.features.shipping.bottomsheet.data.entity.Courier
import com.franzandel.selleverything.features.shipping.bottomsheet.delivery.data.entity.Delivery

/**
 * Created by Franz Andel on 27/09/20.
 * Android Engineer
 */

object DeliveryList {
    private val nextDayCouriers = listOf(
        Courier("SiCepat", "13000")
    )

    private val regularCouriers = listOf(
        Courier("AnterAja", "20000"),
        Courier("JNE Reg", "18000")
    )

    private val cargoCouriers = listOf(
        Courier("JNE", "25000")
    )

    val deliveries = listOf(
        Delivery("Bebas Ongkir (3-5 hari)", "", ""),
        Delivery("Next Day (1 hari)", "13000", "13000", nextDayCouriers),
        Delivery("Reguler (2-4 hari)", "18000", "20000", regularCouriers),
        Delivery("Kargo (2-4 hari)", "25000", "25000", cargoCouriers)
    )
}