package com.franzandel.selleverything.features.detail.data.repository

import com.franzandel.selleverything.data.database.dao.CartProductDao
import com.franzandel.selleverything.data.entity.Product

class DetailRepositoryImpl(private val cartProductDao: CartProductDao) : DetailRepository {

    override suspend fun insertToCart(product: Product) {
        cartProductDao.insert(product)
    }
}