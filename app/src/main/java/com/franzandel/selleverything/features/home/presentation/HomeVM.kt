package com.franzandel.selleverything.features.home.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import com.franzandel.selleverything.base.vm.ProductsVM
import com.franzandel.selleverything.data.database.AppDatabase
import com.franzandel.selleverything.data.entity.Product
import com.franzandel.selleverything.features.cart.data.repository.CartRepository
import com.franzandel.selleverything.features.cart.data.repository.CartRepositoryImpl

/**
 * Created by Franz Andel on 01/12/20.
 * Android Engineer
 */

class HomeVM(application: Application) : ProductsVM(application) {

    private val cartProductDao = AppDatabase.invoke(application.applicationContext).cartProductDao()
    private val cartRepository: CartRepository = CartRepositoryImpl(cartProductDao)
    val cartProducts: LiveData<List<Product>> = cartRepository.cartProducts
}