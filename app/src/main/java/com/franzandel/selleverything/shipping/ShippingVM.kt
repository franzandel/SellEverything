package com.franzandel.selleverything.shipping

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.franzandel.selleverything.data.constants.NumberConstants
import com.franzandel.selleverything.data.entity.MultiType
import com.franzandel.selleverything.data.entity.ShippingFooter
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

    private val _editedSummaryProducts = MutableLiveData<List<MultiType<Any>>>()
    val editedSummaryProducts: LiveData<List<MultiType<Any>>> = _editedSummaryProducts

    private val _validateShippingFooter = MutableLiveData<Int>()
    val validateShippingFooter: LiveData<Int> = _validateShippingFooter

    fun processMultiTypeProducts(products: List<Product>) {
        val multiTypeProducts = mutableListOf<MultiType<Any>>()
        val groupedProducts = getGroupedBySellerProducts(products)

        addMultiTypeProductAddress(multiTypeProducts)

        groupedProducts.forEach { groupedProductsMap ->
            addMultiTypeProductHeader(groupedProductsMap, multiTypeProducts)
            addMultiTypeProductContent(groupedProductsMap, multiTypeProducts)
            addMultiTypeProductFooter(groupedProductsMap, multiTypeProducts, products)
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
        multiTypeProducts: MutableList<MultiType<Any>>,
        products: List<Product>
    ) {
        val shippingFooter = ShippingFooter(
            totalProductsPrice = getTotalProductsPrice(groupedProductsMap.value),
            totalShipping = getGroupedBySellerProducts(products).size
        )
        val multiTypeProductFooter = MultiType<Any>(
            data = shippingFooter,
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
            totalPrice = getTotalCheckedProductsPrice(products).toString()
        )
        val multiTypeProductSummary = MultiType<Any>(
            data = shippingSummary,
            section = ShippingSection.SUMMARY
        )
        multiTypeProducts.add(multiTypeProductSummary)
    }

    private fun getTotalProductsPrice(sellerProducts: List<Product>): String {
        return sellerProducts.sumByDouble { product ->
            product.price.getDiscountedPrice(product.discountPercentage) * product.currentQty
        }.toLong().toString()
    }

    fun addTotalShippingPriceProductSummary(
        products: List<Product>,
        oldMultiTypeProducts: List<MultiType<Any>>,
        courierPrice: String
    ) {
        processTotalShippingPriceProductSummary(products, oldMultiTypeProducts, courierPrice, true)
    }

    fun minusTotalShippingPriceProductSummary(
        products: List<Product>,
        oldMultiTypeProducts: List<MultiType<Any>>,
        courierPrice: String
    ) {
        processTotalShippingPriceProductSummary(products, oldMultiTypeProducts, courierPrice, false)
    }

    private fun processTotalShippingPriceProductSummary(
        products: List<Product>,
        oldMultiTypeProducts: List<MultiType<Any>>,
        courierPrice: String,
        isAdd: Boolean
    ) {
        val newMultiTypeProducts = mutableListOf<MultiType<Any>>()
        newMultiTypeProducts.addAll(oldMultiTypeProducts)

        val newShippingPrice = getNewShippingPrice(newMultiTypeProducts, courierPrice, isAdd)

        val shippingSummary = ShippingSummary(
            totalQty = getTotalCheckedProductsQty(products),
            totalPrice = getTotalCheckedProductsPrice(products).toString(),
            totalShippingPrice = newShippingPrice
        )
        val multiTypeProductSummary = MultiType<Any>(
            data = shippingSummary,
            section = ShippingSection.SUMMARY
        )

        newMultiTypeProducts[newMultiTypeProducts.size - 1] = multiTypeProductSummary
        _editedSummaryProducts.value = newMultiTypeProducts
    }

    private fun getNewShippingPrice(
        newMultiTypeProducts: MutableList<MultiType<Any>>,
        courierPrice: String,
        isAdd: Boolean
    ): String {
        val currentShippingSummaryPosition = newMultiTypeProducts.size - 1
        val currentShippingSummary =
            newMultiTypeProducts[currentShippingSummaryPosition].data as ShippingSummary
        val currentShippingPrice = currentShippingSummary.totalShippingPrice.toLong()
        val selectedShippingPrice = courierPrice.toLong()

        return if (isAdd) {
            (currentShippingPrice + selectedShippingPrice).toString()
        } else {
            (currentShippingPrice - selectedShippingPrice).toString()
        }
    }

    fun validateShippingFooters(shippingFooters: List<MultiType<Any>>) {
        shippingFooters.forEach { anyMultiType ->
            if (anyMultiType.section == ShippingSection.FOOTER) {
                val shippingFooter = anyMultiType.data as ShippingFooter
                if (shippingFooter.deliveryType.isEmpty()) {
                    val shippingHeaderPosition = shippingFooter.adapterPosition - 2
                    _validateShippingFooter.value = shippingHeaderPosition
                    return
                }
            }
        }
        _validateShippingFooter.value = NumberConstants.MINUS_ONE
    }
}