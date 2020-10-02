package com.franzandel.selleverything.shipping

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.franzandel.selleverything.data.entity.ShippingAddress
import com.franzandel.selleverything.data.entity.ShippingFooter
import com.franzandel.selleverything.data.entity.ShippingMultiType
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

    private val _multiTypeProducts = MutableLiveData<List<ShippingMultiType<Any>>>()
    val multiTypeProducts: LiveData<List<ShippingMultiType<Any>>> = _multiTypeProducts

    private val _editedSummaryProducts = MutableLiveData<List<ShippingMultiType<Any>>>()
    val editedSummaryProducts: LiveData<List<ShippingMultiType<Any>>> = _editedSummaryProducts

    private val _validateShippingFooter = MutableLiveData<Pair<Int, Boolean?>>()
    val validateShippingFooter: LiveData<Pair<Int, Boolean?>> = _validateShippingFooter

    private val _onValidateShippingFooterSucceed = MutableLiveData<ShippingSummary>()
    val onValidateShippingFooterSucceed: LiveData<ShippingSummary> =
        _onValidateShippingFooterSucceed

    fun processMultiTypeProducts(products: List<Product>) {
        val multiTypeProducts = mutableListOf<ShippingMultiType<Any>>()
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

    private fun addMultiTypeProductAddress(multiTypeProducts: MutableList<ShippingMultiType<Any>>) {
        val multiTypeProductAddress = ShippingMultiType<Any>(
            data = ShippingAddress(),
            section = ShippingSection.ADDRESS
        )
        multiTypeProducts.add(multiTypeProductAddress)
    }

    private fun addMultiTypeProductHeader(
        groupedProductsMap: Map.Entry<String, List<Product>>,
        multiTypeProducts: MutableList<ShippingMultiType<Any>>
    ) {
        val productHeader = Product(
            seller = groupedProductsMap.key,
            location = groupedProductsMap.value[0].location
        )
        val multiTypeProductHeader = ShippingMultiType<Any>(
            data = productHeader,
            section = ShippingSection.HEADER
        )
        multiTypeProducts.add(multiTypeProductHeader)
    }

    private fun addMultiTypeProductContent(
        groupedProductsMap: Map.Entry<String, List<Product>>,
        multiTypeProducts: MutableList<ShippingMultiType<Any>>
    ) {
        groupedProductsMap.value.forEach { product ->
            val productContent = ShippingMultiType<Any>(
                data = product,
                section = ShippingSection.CONTENT
            )
            multiTypeProducts.add(productContent)
        }
    }

    private fun addMultiTypeProductFooter(
        groupedProductsMap: Map.Entry<String, List<Product>>,
        multiTypeProducts: MutableList<ShippingMultiType<Any>>,
        products: List<Product>
    ) {
        val seller = groupedProductsMap.key
        val shippingFooter = ShippingFooter(
            totalProductsPrice = getTotalProductsPrice(groupedProductsMap.value),
            seller = seller,
            totalShipping = getGroupedBySellerProducts(products).size
        )
        val multiTypeProductFooter = ShippingMultiType<Any>(
            data = shippingFooter,
            section = ShippingSection.FOOTER
        )

        multiTypeProducts.add(multiTypeProductFooter)
    }

    private fun addMultiTypeProductSummary(
        products: List<Product>,
        multiTypeProducts: MutableList<ShippingMultiType<Any>>
    ) {
        val shippingSummary = ShippingSummary(
            totalQty = getTotalCheckedProductsQty(products),
            totalPrice = getTotalCheckedProductsPrice(products).toString()
        )
        val multiTypeProductSummary = ShippingMultiType<Any>(
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
        oldMultiTypeProducts: List<ShippingMultiType<Any>>,
        courierPrice: String
    ) {
        processTotalShippingPriceProductSummary(products, oldMultiTypeProducts, courierPrice, true)
    }

    fun minusTotalShippingPriceProductSummary(
        products: List<Product>,
        oldMultiTypeProducts: List<ShippingMultiType<Any>>,
        courierPrice: String
    ) {
        processTotalShippingPriceProductSummary(products, oldMultiTypeProducts, courierPrice, false)
    }

    private fun processTotalShippingPriceProductSummary(
        products: List<Product>,
        oldMultiTypeProducts: List<ShippingMultiType<Any>>,
        courierPrice: String,
        isAdd: Boolean
    ) {
        val newMultiTypeProducts = mutableListOf<ShippingMultiType<Any>>()
        newMultiTypeProducts.addAll(oldMultiTypeProducts)

        val newTotalShippingPrice =
            getNewTotalShippingPrice(newMultiTypeProducts, courierPrice, isAdd)

        val shippingSummary = ShippingSummary(
            totalQty = getTotalCheckedProductsQty(products),
            totalPrice = getTotalCheckedProductsPrice(products).toString(),
            totalShippingPrice = newTotalShippingPrice
        )
        val multiTypeProductSummary = ShippingMultiType<Any>(
            data = shippingSummary,
            section = ShippingSection.SUMMARY
        )

        newMultiTypeProducts[newMultiTypeProducts.size - 1] = multiTypeProductSummary
        _editedSummaryProducts.value = newMultiTypeProducts
    }

    private fun getNewTotalShippingPrice(
        newMultiTypeProducts: MutableList<ShippingMultiType<Any>>,
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

    fun validateShippingFooters(shippingFooters: List<ShippingMultiType<Any>>) {
        shippingFooters.forEach { anyMultiType ->
            if (anyMultiType.section == ShippingSection.ADDRESS) {
                val shippingAddress = anyMultiType.data as ShippingAddress
                if (shippingAddress.address.isEmpty()) {
                    val shippingAddressPosition = shippingFooters.indexOf(anyMultiType)
                    _validateShippingFooter.value = Pair(shippingAddressPosition, true)
                    return
                }
            }

            if (anyMultiType.section == ShippingSection.FOOTER) {
                val shippingFooter = anyMultiType.data as ShippingFooter
                if (shippingFooter.deliveryType.isEmpty()) {
                    val shippingHeaderPosition =
                        getShippingHeaderPosition(shippingFooters, shippingFooter)
                    _validateShippingFooter.value = Pair(shippingHeaderPosition, false)
                    return
                }
            }
        }

        val shippingSummary = shippingFooters.last().data as ShippingSummary
        _onValidateShippingFooterSucceed.value = shippingSummary
    }

    private fun getShippingHeaderPosition(
        shippingFooters: List<ShippingMultiType<Any>>,
        shippingFooter: ShippingFooter
    ): Int {
        return shippingFooters.indexOfFirst {
            if (it.section == ShippingSection.HEADER) {
                val product = it.data as Product
                product.seller == shippingFooter.seller
            } else {
                false
            }
        }
    }
}