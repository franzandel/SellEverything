package com.franzandel.selleverything.features.payment.bottomsheet.data.entity

import com.franzandel.selleverything.features.payment.bottomsheet.data.enums.PaymentMethodSection

/**
 * Created by Franz Andel on 26/09/20.
 * Android Engineer
 */

data class PaymentMethodMultiType<T>(
    val data: T,
    val section: PaymentMethodSection
)