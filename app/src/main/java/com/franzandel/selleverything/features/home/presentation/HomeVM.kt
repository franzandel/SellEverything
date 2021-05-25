package com.franzandel.selleverything.features.home.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.franzandel.selleverything.base.vm.ProductsVM
import com.franzandel.selleverything.data.database.AppDatabase
import com.franzandel.selleverything.data.entity.Product
import com.franzandel.selleverything.external.Result
import com.franzandel.selleverything.features.cart.data.repository.CartRepository
import com.franzandel.selleverything.features.cart.data.repository.CartRepositoryImpl
import com.franzandel.selleverything.features.home.data.HomeNetworkService
import com.franzandel.selleverything.features.home.data.HomeRepository
import com.franzandel.selleverything.features.home.data.HomeRepositoryImpl
import com.readystatesoftware.chuck.ChuckInterceptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Franz Andel on 01/12/20.
 * Android Engineer
 */

class HomeVM(application: Application) : ProductsVM(application) {

    private val cartProductDao = AppDatabase.invoke(application.applicationContext).cartProductDao()
    private val cartRepository: CartRepository = CartRepositoryImpl(cartProductDao)
    val cartProducts: LiveData<List<Product>> = cartRepository.cartProducts

    private val hostname = "sell-everything.herokuapp.com"
    private val certificatePinner = CertificatePinner.Builder()
        .add(hostname, "sha256/Vuy2zjFSPqF5Hz18k88DpUViKGbABaF3vZx5Raghplc=")
        .add(hostname, "sha256/k2v657xBsOVe1PQRwOsHsw3bsGT2VzIqz5K+59sNQws=")
        .add(hostname, "sha256/WoiWRyIOVNa9ihaBciRSC7XHjliYS9VwUGOIud4PB18=")
        .build()

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(ChuckInterceptor(application))
        .certificatePinner(certificatePinner)
        .writeTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()
    private val retrofit = Retrofit.Builder()
//        .baseUrl("http://10.0.2.2:8000/")
        .baseUrl("https://sell-everything.herokuapp.com/")
        .client(okHttp)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    private val homeNetworkService = retrofit.create(HomeNetworkService::class.java)
    private val homeRepository: HomeRepository = HomeRepositoryImpl(homeNetworkService)

    private val _productsSuccessResult = MutableLiveData<List<Product>>()
    val productsSuccessResult: LiveData<List<Product>> = _productsSuccessResult

    private val _productsErrorResult = MutableLiveData<String>()
    val productsErrorResult: LiveData<String> = _productsErrorResult

    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = homeRepository.getProducts()) {
                is Result.Success -> _productsSuccessResult.postValue(result.data)
                is Result.Error -> _productsErrorResult.postValue(result.error.message.toString())
            }
        }
    }
}