package com.franzandel.selleverything.cart

import androidx.lifecycle.LiveData
import com.franzandel.selleverything.data.database.CartProductDao
import com.franzandel.selleverything.newest.Product

class CartRepositoryImpl(private val cartProductDao: CartProductDao) : CartRepository {

    override val cartProducts: LiveData<List<Product>>
        get() = cartProductDao.getAll()
}