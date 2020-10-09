package com.franzandel.selleverything.recyclerview

import android.widget.Filter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.franzandel.selleverything.newest.Product

/**
 * Created by Franz Andel on 09/10/20.
 * Android Engineer
 */

class HomeFilter : Filter() {

    private var products = emptyList<Product>()

    private val _filteredProducts = MutableLiveData<List<Product>>()
    val filteredProducts: LiveData<List<Product>> = _filteredProducts

    fun setProducts(products: List<Product>) {
        this.products = products
    }

    override fun performFiltering(charSequence: CharSequence): FilterResults {
        val typedText = charSequence.toString()
        val filteredProducts = getFilteredProducts(typedText)

        return FilterResults().apply {
            values = filteredProducts
        }
    }

    override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
        val products = filterResults.values as List<Product>
        if (charSequence.isNotEmpty() || products.isNotEmpty()) {
            _filteredProducts.value = products
        }
    }

    private fun getFilteredProducts(typedText: String): List<Product> =
        if (typedText.isEmpty()) {
            products
        } else {
            getFilteredProductsIfNotEmptyTypedText(typedText)
        }

    private fun getFilteredProductsIfNotEmptyTypedText(typedText: String): List<Product> =
        products.filter { product ->
            product.seller.contains(typedText, true) ||
                    product.location.contains(typedText, true) ||
                    product.title.contains(typedText, true)
        }
}