package com.franzandel.selleverything.shipping

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.franzandel.selleverything.data.entity.MultiType
import com.franzandel.selleverything.data.enums.ShippingSection
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
        val multiTypeProducts = mutableListOf<MultiType<Product>>()
        val groupedProducts = getGroupedBySellerProducts(products)

        addMultiTypeProductAddress(multiTypeProducts)

        groupedProducts.forEach { groupedProductsMap ->
            addMultiTypeProductHeader(groupedProductsMap, multiTypeProducts)
            addMultiTypeProductContent(groupedProductsMap, multiTypeProducts)
            addMultiTypeProductFooter(groupedProductsMap, multiTypeProducts)
        }

        addMultiTypeProductSummary(products, multiTypeProducts)
        _multiTypeProducts.value = multiTypeProducts
    }

    private fun getGroupedBySellerProducts(products: List<Product>): Map<String, List<Product>> =
        products
            .filter { product ->
                product.isChecked
            }
            .groupBy { product ->
                product.seller
            }

    private fun addMultiTypeProductAddress(multiTypeProducts: MutableList<MultiType<Product>>) {
        val multiTypeProductAddress = MultiType<Product>(
            section = ShippingSection.ADDRESS
        )
        multiTypeProducts.add(multiTypeProductAddress)
    }

    private fun addMultiTypeProductHeader(
        groupedProductsMap: Map.Entry<String, List<Product>>,
        multiTypeProducts: MutableList<MultiType<Product>>
    ) {
        val productHeader = Product(
            seller = groupedProductsMap.key,
            location = groupedProductsMap.value[0].location
        )
        val multiTypeProductHeader = MultiType(
            data = productHeader,
            section = ShippingSection.HEADER
        )
        multiTypeProducts.add(multiTypeProductHeader)
    }

    private fun addMultiTypeProductContent(
        groupedProductsMap: Map.Entry<String, List<Product>>,
        multiTypeProducts: MutableList<MultiType<Product>>
    ) {
        groupedProductsMap.value.forEach { product ->
            val productContent = MultiType(
                data = product,
                section = ShippingSection.CONTENT
            )
            multiTypeProducts.add(productContent)
        }
    }

    private fun addMultiTypeProductFooter(
        groupedProductsMap: Map.Entry<String, List<Product>>,
        multiTypeProducts: MutableList<MultiType<Product>>
    ) {
        val productFooter = Product(price = getSellerSubTotalPrice(groupedProductsMap.value))
        val multiTypeProductFooter = MultiType(
            data = productFooter,
            section = ShippingSection.FOOTER
        )

        multiTypeProducts.add(multiTypeProductFooter)
    }

    private fun addMultiTypeProductSummary(
        products: List<Product>,
        multiTypeProducts: MutableList<MultiType<Product>>
    ) {
        val productSummary = Product(
            currentQty = getTotalCheckedProductsQty(products).toInt(),
            price = getTotalCheckedProductsPrice(products)
        )
        val multiTypeProductSummary = MultiType(
            data = productSummary,
            section = ShippingSection.SUMMARY
        )
        multiTypeProducts.add(multiTypeProductSummary)
    }

    private fun getSellerSubTotalPrice(sellerProducts: List<Product>): String {
        return sellerProducts.sumByDouble { product ->
            product.price.getDiscountedPrice(product.discountPercentage) * product.currentQty
        }.toLong().toString()
    }
}