package com.franzandel.selleverything.detail

import com.franzandel.selleverything.data.database.CartProductDao
import com.franzandel.selleverything.newest.Product

class DetailRepositoryImpl(private val cartProductDao: CartProductDao) : DetailRepository {

    override suspend fun insertToCart(product: Product) {
        cartProductDao.insert(product)
    }
}