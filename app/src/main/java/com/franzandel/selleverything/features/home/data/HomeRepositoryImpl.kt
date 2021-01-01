package com.franzandel.selleverything.features.home.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.franzandel.selleverything.data.entity.Product
import kotlinx.coroutines.flow.flow

class HomeRepositoryImpl(private val homeNetworkService: HomeNetworkService) : HomeRepository {

    override suspend fun getProducts(): LiveData<List<Product>> =
        flow {
            homeNetworkService.getProducts().apply {
                if (isSuccessful) {
                    emit(body()!!.result)
                }
            }
        }.asLiveData()
}