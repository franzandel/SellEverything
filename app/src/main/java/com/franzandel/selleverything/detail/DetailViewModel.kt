package com.franzandel.selleverything.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.franzandel.selleverything.data.database.AppDatabase
import com.franzandel.selleverything.newest.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Franz Andel on 15/09/20.
 * Android Engineer
 */

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val cartProductDao = AppDatabase.invoke(application.applicationContext).cartProductDao()
    private val detailRepository: DetailRepository = DetailRepositoryImpl(cartProductDao)

    fun insertToCart(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        detailRepository.insertToCart(product)
    }
}