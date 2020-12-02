package com.franzandel.selleverything.features.detail.data.repository

import androidx.lifecycle.LiveData
import com.franzandel.selleverything.data.database.dao.CartProductDao
import com.franzandel.selleverything.data.entity.Product

class DetailRepositoryImpl(private val cartProductDao: CartProductDao) : DetailRepository {

    override val cartProducts: LiveData<List<Product>>
        get() = cartProductDao.getAll()

    override suspend fun insertToCart(product: Product) {
        cartProductDao.insert(product)
    }
}