package com.franzandel.selleverything.shipping

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.franzandel.selleverything.data.entity.MultiType
import com.franzandel.selleverything.data.entity.ShippingSummary
import com.franzandel.selleverything.data.enums.ShippingSection
import com.franzandel.selleverything.extension.getDiscountedPrice
import com.franzandel.selleverything.newest.Product
import com.franzandel.selleverything.vm.ProductsVM

/**
 * Created by Franz Andel on 26/09/20.
 * Android Engineer
 */

class ShippingVM(application: Application) : ProductsVM(application) {

    private val _multiTypeProducts = MutableLiveData<List<MultiType<Any>>>()
    val multiTypeProducts: LiveData<List<MultiType<Any>>> = _multiTypeProducts

    private val _multiTypeProducts2 = MutableLiveData<List<MultiType<Any>>>()
    val multiTypeProducts2: LiveData<List<MultiType<Any>>> = _multiTypeProducts2

    fun processMultiTypeProducts(products: List<Product>) {
        val multiTypeProducts = mutableListOf<MultiType<Any>>()
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

    private fun addMultiTypeProductAddress(multiTypeProducts: MutableList<MultiType<Any>>) {
        val multiTypeProductAddress = MultiType<Any>(
            section = ShippingSection.ADDRESS
        )
        multiTypeProducts.add(multiTypeProductAddress)
    }

    private fun addMultiTypeProductHeader(
        groupedProductsMap: Map.Entry<String, List<Product>>,
        multiTypeProducts: MutableList<MultiType<Any>>
    ) {
        val productHeader = Product(
            seller = groupedProductsMap.key,
            location = groupedProductsMap.value[0].location
        )
        val multiTypeProductHeader = MultiType<Any>(
            data = productHeader,
            section = ShippingSection.HEADER
        )
        multiTypeProducts.add(multiTypeProductHeader)
    }

    private fun addMultiTypeProductContent(
        groupedProductsMap: Map.Entry<String, List<Product>>,
        multiTypeProducts: MutableList<MultiType<Any>>
    ) {
        groupedProductsMap.value.forEach { product ->
            val productContent = MultiType<Any>(
                data = product,
                section = ShippingSection.CONTENT
            )
            multiTypeProducts.add(productContent)
        }
    }

    private fun addMultiTypeProductFooter(
        groupedProductsMap: Map.Entry<String, List<Product>>,
        multiTypeProducts: MutableList<MultiType<Any>>
    ) {
        val productFooter = Product(price = getSellerSubTotalPrice(groupedProductsMap.value))
        val multiTypeProductFooter = MultiType<Any>(
            data = productFooter,
            section = ShippingSection.FOOTER
        )

        multiTypeProducts.add(multiTypeProductFooter)
    }

    private fun addMultiTypeProductSummary(
        products: List<Product>,
        multiTypeProducts: MutableList<MultiType<Any>>
    ) {
        val shippingSummary = ShippingSummary(
            totalQty = getTotalCheckedProductsQty(products),
            totalPrice = getTotalCheckedProductsPrice(products)
        )
        val multiTypeProductSummary = MultiType<Any>(
            data = shippingSummary,
            section = ShippingSection.SUMMARY
        )
        multiTypeProducts.add(multiTypeProductSummary)
    }

    private fun getSellerSubTotalPrice(sellerProducts: List<Product>): String {
        return sellerProducts.sumByDouble { product ->
            product.price.getDiscountedPrice(product.discountPercentage) * product.currentQty
        }.toLong().toString()
    }

    fun addTotalOrderPriceProductSummary(
        products: List<Product>,
        multiTypeProducts: List<MultiType<Any>>,
        courierPrice: String
    ) {
        val mutableMultiTypeProducts = mutableListOf<MultiType<Any>>()
        mutableMultiTypeProducts.addAll(multiTypeProducts)

        val currentShippingSummary =
            mutableMultiTypeProducts[mutableMultiTypeProducts.size - 1].data as ShippingSummary
        val currentShippingPrice = currentShippingSummary.totalOrderPrice.toLong()
        val selectedShippingPrice = courierPrice.toLong()
        val newShippingPrice = currentShippingPrice + selectedShippingPrice

        val shippingSummary = ShippingSummary(
            totalQty = getTotalCheckedProductsQty(products),
            totalPrice = getTotalCheckedProductsPrice(products),
            totalOrderPrice = newShippingPrice.toString()
        )
        val multiTypeProductSummary = MultiType<Any>(
            data = shippingSummary,
            section = ShippingSection.SUMMARY
        )
        mutableMultiTypeProducts[mutableMultiTypeProducts.size - 1] = multiTypeProductSummary
        _multiTypeProducts2.value = mutableMultiTypeProducts
    }

    fun minusTotalOrderPriceProductSummary(
        products: List<Product>,
        multiTypeProducts: List<MultiType<Any>>,
        courierPrice: String
    ) {
        val mutableMultiTypeProducts = mutableListOf<MultiType<Any>>()
        mutableMultiTypeProducts.addAll(multiTypeProducts)

        val currentShippingSummary =
            mutableMultiTypeProducts[mutableMultiTypeProducts.size - 1].data as ShippingSummary
        val currentShippingPrice = currentShippingSummary.totalOrderPrice.toLong()
        val selectedShippingPrice = courierPrice.toLong()
        val newShippingPrice = currentShippingPrice - selectedShippingPrice

        val shippingSummary = ShippingSummary(
            totalQty = getTotalCheckedProductsQty(products),
            totalPrice = getTotalCheckedProductsPrice(products),
            totalOrderPrice = newShippingPrice.toString()
        )
        val multiTypeProductSummary = MultiType<Any>(
            data = shippingSummary,
            section = ShippingSection.SUMMARY
        )
        mutableMultiTypeProducts[mutableMultiTypeProducts.size - 1] = multiTypeProductSummary
        _multiTypeProducts2.value = mutableMultiTypeProducts
    }
}