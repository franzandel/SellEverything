package com.franzandel.selleverything.features.home.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.franzandel.selleverything.base.vm.ProductsVM
import com.franzandel.selleverything.data.database.AppDatabase
import com.franzandel.selleverything.data.entity.Product
import com.franzandel.selleverything.features.cart.data.repository.CartRepository
import com.franzandel.selleverything.features.cart.data.repository.CartRepositoryImpl
import com.franzandel.selleverything.features.home.data.HomeNetworkService
import com.franzandel.selleverything.features.home.data.HomeRepository
import com.franzandel.selleverything.features.home.data.HomeRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Franz Andel on 01/12/20.
 * Android Engineer
 */

class HomeVM(application: Application) : ProductsVM(application) {

    private val cartProductDao = AppDatabase.invoke(application.applicationContext).cartProductDao()
    private val cartRepository: CartRepository = CartRepositoryImpl(cartProductDao)
    val cartProducts: LiveData<List<Product>> = cartRepository.cartProducts

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8000/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    private val homeNetworkService = retrofit.create(HomeNetworkService::class.java)
    private val homeRepository: HomeRepository = HomeRepositoryImpl(homeNetworkService)

    private val _productsResult = MediatorLiveData<List<Product>>()
    val productsResult: LiveData<List<Product>> = _productsResult

    private var _productsSource: LiveData<List<Product>> = MutableLiveData()

    fun getProducts() {
        _productsSource = homeRepository.getProducts()

        _productsResult.addSource(_productsSource) { products ->
            _productsResult.value = products
        }
    }
}