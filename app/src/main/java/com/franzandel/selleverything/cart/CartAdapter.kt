package com.franzandel.selleverything.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ListAdapter
import com.franzandel.selleverything.R
import com.franzandel.selleverything.newest.Product

/**
 * Created by Franz Andel on 17/09/20.
 * Android Engineer
 */

class CartAdapter(private val context: Context) :
    ListAdapter<Product, CartViewHolder>(CartDiffCallback()) {

    private lateinit var cartViewHolder: CartViewHolder
    private val activity = context as AppCompatActivity

    private val _onCheckClicked = MutableLiveData<List<Product>>()
    val onCheckClicked: LiveData<List<Product>> = _onCheckClicked

    private val _onQtyMinusClicked = MutableLiveData<Product>()
    val onQtyMinusClicked: LiveData<Product> = _onQtyMinusClicked

    private val _onQtyPlusClicked = MutableLiveData<Product>()
    val onQtyPlusClicked: LiveData<Product> = _onQtyPlusClicked

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false)
        cartViewHolder = CartViewHolder(view)
        setupObserver()
        return cartViewHolder
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = currentList[position]
        holder.bind(product)
    }

    private fun setupObserver() {
        cartViewHolder.onCheckClicked.observe(activity, Observer {
            _onCheckClicked.value = currentList
        })

        cartViewHolder.onQtyMinusClicked.observe(activity, Observer { product ->
            _onQtyMinusClicked.value = product
        })

        cartViewHolder.onQtyPlusClicked.observe(activity, Observer { product ->
            _onQtyPlusClicked.value = product
        })
    }

    fun checkAllProducts(isChecked: Boolean) {
        currentList.forEach { product ->
            product.isChecked = isChecked
        }
        notifyDataSetChanged()
    }
}