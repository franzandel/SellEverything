package com.franzandel.selleverything.data.entity

import com.franzandel.selleverything.data.enums.PaymentSection

/**
 * Created by Franz Andel on 26/09/20.
 * Android Engineer
 */

data class PaymentMultiType<T>(
    val data: T,
    val section: PaymentSection
)