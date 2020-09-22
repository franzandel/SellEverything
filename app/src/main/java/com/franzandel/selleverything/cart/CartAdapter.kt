package com.franzandel.selleverything.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ListAdapter
import com.franzandel.selleverything.R
import com.franzandel.selleverything.databinding.ItemCartBinding
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

    private val _onQtyChanged = MutableLiveData<Pair<Product, List<Product>>>()
    val onQtyChanged: LiveData<Pair<Product, List<Product>>> = _onQtyChanged

    private val _onDeleteClicked = MutableLiveData<Product>()
    val onDeleteClicked: LiveData<Product> = _onDeleteClicked

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val itemCartBinding: ItemCartBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_cart, parent, false)
        cartViewHolder = CartViewHolder(itemCartBinding)
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

        cartViewHolder.onQtyChanged.observe(activity, Observer { (position, qty) ->
            val product = currentList[position]
            product.currentQty = qty.toInt()
            _onQtyChanged.value = Pair(product, currentList)
        })

        cartViewHolder.onDeleteClicked.observe(activity, Observer { product ->
            _onDeleteClicked.value = product
        })
    }

    fun checkAllProducts(isChecked: Boolean) {
        currentList.forEach { product ->
            product.isChecked = isChecked
        }
        notifyDataSetChanged()
    }
}