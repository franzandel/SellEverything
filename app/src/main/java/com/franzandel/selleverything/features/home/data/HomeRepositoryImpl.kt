package com.franzandel.selleverything.features.home.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.franzandel.selleverything.data.entity.Product
import com.franzandel.selleverything.features.home.presentation.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepositoryImpl(private val homeNetworkService: HomeNetworkService) : HomeRepository {

    override fun getProducts(): LiveData<List<Product>> {
        val productsResult = MutableLiveData<List<Product>>()
        homeNetworkService.getProducts().enqueue(object : Callback<BaseResponse> {
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                productsResult.value = response.body()!!.result
            }
        })

        return productsResult
    }
}