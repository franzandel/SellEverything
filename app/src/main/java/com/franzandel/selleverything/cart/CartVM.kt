package com.franzandel.selleverything.cart

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.franzandel.selleverything.data.database.AppDatabase
import com.franzandel.selleverything.data.entity.CartMultiType
import com.franzandel.selleverything.data.enums.CartSection
import com.franzandel.selleverything.extension.getDiscountedPrice
import com.franzandel.selleverything.extension.getFormattedIDNPrice
import com.franzandel.selleverything.extension.removeSpecialCharacter
import com.franzandel.selleverything.extension.removeText
import com.franzandel.selleverything.newest.Product
import com.franzandel.selleverything.vm.ProductsVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Franz Andel on 16/09/20.
 * Android Engineer
 */

class CartVM(application: Application) : ProductsVM(application) {

    private val cartProductDao = AppDatabase.invoke(application.applicationContext).cartProductDao()
    private val cartRepository: CartRepository = CartRepositoryImpl(cartProductDao)
    val cartProducts: LiveData<List<Product>> = cartRepository.cartProducts

    private val _multiTypeProducts = MutableLiveData<List<CartMultiType<Product>>>()
    val multiTypeProducts: LiveData<List<CartMultiType<Product>>> = _multiTypeProducts

    private val _onCheckAllProducts = MutableLiveData<Unit>()
    val onCheckAllProducts: LiveData<Unit> = _onCheckAllProducts

    @JvmName("totalCheckedProductsCount")
    fun getTotalCheckedProductsCount(products: List<Product>): String = products.count { product ->
        product.isChecked
    }.toString()

    @JvmName("totalCheckedCartMultiTypeProductsCount")
    fun getTotalCheckedProductsCount(products: List<CartMultiType<Product>>): String =
        products.count { product ->
            product.data.isChecked && product.section == CartSection.CONTENT
        }.toString()

    fun getTotalProductsPriceAfterMinusClicked(
        product: Product,
        currentTotalPrice: String
    ): String {
        val existingTotalPrice = currentTotalPrice.removeSpecialCharacter().removeText().toLong()
        val productPrice = product.price.getDiscountedPrice(product.discountPercentage).toLong()
        val lastTotalPrice = existingTotalPrice - productPrice
        return lastTotalPrice.getFormattedIDNPrice()
    }

    fun getTotalProductsPriceAfterPlusClicked(product: Product, currentTotalPrice: String): String {
        val existingTotalPrice = currentTotalPrice.removeSpecialCharacter().removeText().toLong()
        val productPrice = product.price.getDiscountedPrice(product.discountPercentage).toLong()
        val lastTotalPrice = existingTotalPrice + productPrice
        return lastTotalPrice.getFormattedIDNPrice()
    }

    fun getTotalCheckedProductsCountAfterMinusClicked(currentCheckedProductsCount: String): String {
        val existingProductsCount =
            currentCheckedProductsCount.removeSpecialCharacter().removeText()
        return (existingProductsCount.toInt() - 1).toString()
    }

    fun getTotalCheckedProductsCountAfterPlusClicked(currentCheckedProductsCount: String): String {
        val existingProductsCount =
            currentCheckedProductsCount.removeSpecialCharacter().removeText()
        return (existingProductsCount.toInt() + 1).toString()
    }

    fun updateCart(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.updateCart(product)
        }
    }

    fun deleteAllFromCart(products: List<Product>) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteAllFromCart(products)
        }
    }

    fun deleteFromCart(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteFromCart(product)
        }
    }

    fun processMultiTypeProducts(products: List<Product>) {
        val multiTypeProducts = mutableListOf<CartMultiType<Product>>()
        val groupedProducts = getGroupedBySellerProducts(products)

        groupedProducts.forEach { groupedProductsMap ->
            addMultiTypeProductHeader(groupedProductsMap, multiTypeProducts)
            addMultiTypeProductContent(groupedProductsMap, multiTypeProducts)
        }

        _multiTypeProducts.value = multiTypeProducts
    }

    private fun addMultiTypeProductHeader(
        groupedProductsMap: Map.Entry<String, List<Product>>,
        multiTypeProducts: MutableList<CartMultiType<Product>>
    ) {
        val productHeader = Product(
            seller = groupedProductsMap.key,
            location = groupedProductsMap.value[0].location
        )
        val multiTypeProductHeader = CartMultiType(
            data = productHeader,
            section = CartSection.HEADER
        )
        multiTypeProducts.add(multiTypeProductHeader)
    }

    private fun addMultiTypeProductContent(
        groupedProductsMap: Map.Entry<String, List<Product>>,
        multiTypeProducts: MutableList<CartMultiType<Product>>
    ) {
        groupedProductsMap.value.forEach { product ->
            val productContent = CartMultiType(
                data = product,
                section = CartSection.CONTENT
            )
            multiTypeProducts.add(productContent)
        }
    }

    @JvmName("totalCheckedCartMultiTypeProductsQty")
    fun getTotalCheckedProductsQty(products: List<CartMultiType<Product>>): String =
        products.filter { product ->
            product.data.isChecked && product.section == CartSection.CONTENT
        }.sumBy { product ->
            product.data.currentQty
        }.toString()

    @JvmName("totalCheckedCartMultiTypeProductsPrice")
    fun getTotalCheckedProductsPrice(products: List<CartMultiType<Product>>): Long =
        products.filter { product ->
            product.data.isChecked
        }.sumByDouble { product ->
            product.data.price.getDiscountedPrice(product.data.discountPercentage) * product.data.currentQty
        }.toLong()

    fun checkAllProductsPerSeller(
        seller: String,
        isChecked: Boolean,
        multiTypeProducts: List<CartMultiType<Product>>
    ) {
        multiTypeProducts.filter { multiTypeProduct ->
            multiTypeProduct.data.seller == seller
        }.forEach { multiTypeProduct ->
            multiTypeProduct.data.isChecked = isChecked
        }
    }

    fun getTotalProductsCount(multiTypeProducts: List<CartMultiType<Product>>): String =
        multiTypeProducts.count { multiTypeProduct ->
            multiTypeProduct.section == CartSection.CONTENT
        }.toString()

    fun getTotalProductsCountPerSeller(
        seller: String,
        multiTypeProducts: List<CartMultiType<Product>>
    ): String {
        return multiTypeProducts.count { multiTypeProduct ->
            multiTypeProduct.data.seller == seller && multiTypeProduct.section == CartSection.CONTENT
        }.toString()
    }

    fun getTotalProductsCheckedCountPerSeller(
        seller: String,
        multiTypeProducts: List<CartMultiType<Product>>
    ): String {
        return multiTypeProducts.count { multiTypeProduct ->
            multiTypeProduct.data.seller == seller &&
                    multiTypeProduct.section == CartSection.CONTENT &&
                    multiTypeProduct.data.isChecked
        }.toString()
    }

    fun checkProductsHeader(
        seller: String,
        multiTypeProducts: List<CartMultiType<Product>>,
        isChecked: Boolean
    ) {
        multiTypeProducts.filter { multiTypeProduct ->
            multiTypeProduct.data.seller == seller && multiTypeProduct.section == CartSection.HEADER
        }.forEach { multiTypeProduct ->
            multiTypeProduct.data.isChecked = isChecked
        }
    }

    fun checkAllProducts(multiTypeProducts: List<CartMultiType<Product>>, isChecked: Boolean) {
        multiTypeProducts.forEach { multiTypeProduct ->
            multiTypeProduct.data.isChecked = isChecked
        }
        _onCheckAllProducts.value = Unit
    }
}