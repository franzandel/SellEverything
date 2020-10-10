package com.franzandel.selleverything.features.cart.data.repository

import androidx.lifecycle.LiveData
import com.franzandel.selleverything.data.database.dao.CartProductDao
import com.franzandel.selleverything.data.entity.Product

class CartRepositoryImpl(private val cartProductDao: CartProductDao) : CartRepository {

    override val cartProducts: LiveData<List<Product>>
        get() = cartProductDao.getAll()

    override suspend fun updateCart(product: Product) {
        cartProductDao.update(product)
    }

    override suspend fun deleteFromCart(product: Product) {
        cartProductDao.delete(product)
    }

    override suspend fun deleteAllFromCart(products: List<Product>) {
        cartProductDao.deleteAll(products)
    }
}