package com.franzandel.selleverything.features.detail.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.franzandel.selleverything.data.database.AppDatabase
import com.franzandel.selleverything.data.entity.Product
import com.franzandel.selleverything.features.detail.data.repository.DetailRepository
import com.franzandel.selleverything.features.detail.data.repository.DetailRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Franz Andel on 15/09/20.
 * Android Engineer
 */

class DetailVM(application: Application) : AndroidViewModel(application) {

    private val cartProductDao = AppDatabase.invoke(application.applicationContext).cartProductDao()
    private val detailRepository: DetailRepository = DetailRepositoryImpl(cartProductDao)

    fun insertToCart(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        detailRepository.insertToCart(product)
    }
}