package com.franzandel.selleverything.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.franzandel.selleverything.extension.getDiscountedPrice
import com.franzandel.selleverything.extension.getFormattedIDNPrice
import com.franzandel.selleverything.newest.Product

/**
 * Created by Franz Andel on 26/09/20.
 * Android Engineer
 */

abstract class ProductsVM(application: Application) : AndroidViewModel(application) {

    fun getTotalCheckedProductsQty(products: List<Product>): String =
        products.filter { product ->
            product.isChecked
        }.sumBy { product ->
            product.currentQty
        }.toString()

    fun getTotalCheckedProductsPrice(products: List<Product>): String =
        products.filter { product ->
            product.isChecked
        }.sumByDouble { product ->
            product.price.getDiscountedPrice(product.discountPercentage) * product.currentQty
        }.toLong().getFormattedIDNPrice()
}