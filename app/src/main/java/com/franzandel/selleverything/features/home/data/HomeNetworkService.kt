package com.franzandel.selleverything.features.home.data

import com.franzandel.selleverything.features.home.presentation.BaseResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Franz Andel on 31/12/20.
 * Android Engineer
 */

interface HomeNetworkService {
    @GET("products")
    suspend fun getProducts(): Response<BaseResponse>
}