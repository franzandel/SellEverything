package com.franzandel.selleverything.data.entity

import com.franzandel.selleverything.data.enums.AdapterSection

/**
 * Created by Franz Andel on 26/09/20.
 * Android Engineer
 */

data class MultiType<T>(
    val data: T? = null,
    val section: AdapterSection
)