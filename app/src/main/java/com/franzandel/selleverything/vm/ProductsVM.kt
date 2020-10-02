package com.franzandel.selleverything.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.franzandel.selleverything.extension.getDiscountedPrice
import com.franzandel.selleverything.newest.Product

/**
 * Created by Franz Andel on 26/09/20.
 * Android Engineer
 */

abstract class ProductsVM(application: Application) : AndroidViewModel(application) {

    @JvmName("checkedProductsQty")
    fun getTotalCheckedProductsQty(products: List<Product>): String =
        products.filter { product ->
            product.isChecked
        }.sumBy { product ->
            product.currentQty
        }.toString()

    @JvmName("checkedProductsPrice")
    fun getTotalCheckedProductsPrice(products: List<Product>): Long =
        products.filter { product ->
            product.isChecked
        }.sumByDouble { product ->
            product.price.getDiscountedPrice(product.discountPercentage) * product.currentQty
        }.toLong()

    fun getGroupedBySellerProducts(products: List<Product>): Map<String, List<Product>> =
        products
            .filter { product ->
                product.isChecked
            }
            .groupBy { product ->
                product.seller
            }
}