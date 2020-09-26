package com.franzandel.selleverything.shipping

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.franzandel.selleverything.data.entity.MultiType
import com.franzandel.selleverything.data.enums.AdapterSection
import com.franzandel.selleverything.extension.getDiscountedPrice
import com.franzandel.selleverything.newest.Product
import com.franzandel.selleverything.vm.ProductsVM

/**
 * Created by Franz Andel on 26/09/20.
 * Android Engineer
 */

class ShippingVM(application: Application) : ProductsVM(application) {

    private val _multiTypeProducts = MutableLiveData<List<MultiType<Product>>>()
    val multiTypeProducts: LiveData<List<MultiType<Product>>> = _multiTypeProducts

    fun processMultiTypeProducts(products: List<Product>) {
        fun getGroupedBySellerProducts(): Map<String, List<Product>> = products
            .filter { product ->
                product.isChecked
            }
            .groupBy { product ->
                product.seller
            }

        val multiTypeProducts = mutableListOf<MultiType<Product>>()
        val groupedProducts = getGroupedBySellerProducts()

        groupedProducts.forEach { groupedProductsMap ->
            val productHeader = Product(
                seller = groupedProductsMap.key,
                location = groupedProductsMap.value[0].location
            )
            val multiTypeProductHeader = MultiType(productHeader, AdapterSection.HEADER)
            multiTypeProducts.add(multiTypeProductHeader)

            groupedProductsMap.value.forEach { product ->
                val productContent = MultiType(data = product, section = AdapterSection.CONTENT)
                multiTypeProducts.add(productContent)
            }

            val productFooter = Product(price = getSellerSubTotalPrice(groupedProductsMap.value))
            val multiTypeProductFooter = MultiType(productFooter, AdapterSection.FOOTER)

            multiTypeProducts.add(multiTypeProductFooter)
        }
        _multiTypeProducts.value = multiTypeProducts
    }

    private fun getSellerSubTotalPrice(sellerProducts: List<Product>): String {
        return sellerProducts.sumByDouble { product ->
            product.price.getDiscountedPrice(product.discountPercentage) * product.currentQty
        }.toLong().toString()
    }
}