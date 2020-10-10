package com.franzandel.selleverything.features.cart.presentation

import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.R
import com.franzandel.selleverything.base.BaseListAdapter
import com.franzandel.selleverything.data.entity.Product
import com.franzandel.selleverything.databinding.ItemCartContentBinding
import com.franzandel.selleverything.features.cart.data.entity.CartMultiType
import com.franzandel.selleverything.features.cart.data.enums.CartSection

/**
 * Created by Franz Andel on 17/09/20.
 * Android Engineer
 */

class CartAdapter(context: Context) :
    BaseListAdapter<RecyclerView.ViewHolder, CartMultiType<Product>>(
        context,
        { CartDiffCallback() }
    ) {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_CONTENT = 1
    }

    private val activity = context as AppCompatActivity

    private val _onCheckSellerClicked =
        MutableLiveData<Triple<String, Boolean, List<CartMultiType<Product>>>>()
    val onCheckSellerClicked: LiveData<Triple<String, Boolean, List<CartMultiType<Product>>>> =
        _onCheckSellerClicked

    private val _onCheckProductClicked =
        MutableLiveData<Pair<List<CartMultiType<Product>>, String>>()
    val onCheckProductClicked: LiveData<Pair<List<CartMultiType<Product>>, String>> =
        _onCheckProductClicked

    private val _onQtyMinusClicked = MutableLiveData<Product>()
    val onQtyMinusClicked: LiveData<Product> = _onQtyMinusClicked

    private val _onQtyPlusClicked = MutableLiveData<Product>()
    val onQtyPlusClicked: LiveData<Product> = _onQtyPlusClicked

    private val _onQtyChanged =
        MutableLiveData<Pair<CartMultiType<Product>, List<CartMultiType<Product>>>>()
    val onQtyChanged: LiveData<Pair<CartMultiType<Product>, List<CartMultiType<Product>>>> =
        _onQtyChanged

    private val _onDeleteClicked = MutableLiveData<Product>()
    val onDeleteClicked: LiveData<Product> = _onDeleteClicked

    override fun getItemLayoutId(): Int =
        when (viewType) {
            TYPE_HEADER -> R.layout.item_cart_header
            else -> R.layout.item_cart_content
        }

    override fun getViewHolder(view: View): RecyclerView.ViewHolder =
        CartHeaderViewHolder(view)

    override fun getViewHolder(viewDataBinding: ViewDataBinding): RecyclerView.ViewHolder =
        CartContentViewHolder(viewDataBinding as ItemCartContentBinding)

    override fun getItemViewType(position: Int): Int =
        when (currentList[position].section) {
            CartSection.HEADER -> {
                setIsDataBindingNeeded(false)
                TYPE_HEADER
            }
            CartSection.CONTENT -> {
                setIsDataBindingNeeded(true)
                TYPE_CONTENT
            }
        }

    override fun actionOnCreateViewHolder() {
        when (viewType) {
            TYPE_HEADER -> setupCartHeaderObserver()
            else -> setupCartContentObserver()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = currentList[position]

        when (product.section) {
            CartSection.HEADER -> (holder as CartHeaderViewHolder).bind(product.data)
            CartSection.CONTENT -> (holder as CartContentViewHolder).bind(product.data)
        }
    }

    private fun setupCartHeaderObserver() {
        (viewHolder as CartHeaderViewHolder).onCheckSellerClicked.observe(
            activity,
            Observer { (seller, isChecked) ->
                _onCheckSellerClicked.value = Triple(seller, isChecked, currentList)
            })
    }

    private fun setupCartContentObserver() {
        val cartContentViewHolder = viewHolder as CartContentViewHolder
        cartContentViewHolder.onCheckProductClicked.observe(activity, Observer { seller ->
            _onCheckProductClicked.value = Pair(currentList, seller)
        })

        cartContentViewHolder.onQtyMinusClicked.observe(activity, Observer { product ->
            _onQtyMinusClicked.value = product
        })

        cartContentViewHolder.onQtyPlusClicked.observe(activity, Observer { product ->
            _onQtyPlusClicked.value = product
        })

        cartContentViewHolder.onQtyChanged.observe(activity, Observer { (position, qty) ->
            val multiTypeProduct = currentList[position]
            multiTypeProduct.data.currentQty = qty.toInt()
            _onQtyChanged.value = Pair(multiTypeProduct, currentList)
        })

        cartContentViewHolder.onDeleteClicked.observe(activity, Observer { product ->
            _onDeleteClicked.value = product
        })
    }
}