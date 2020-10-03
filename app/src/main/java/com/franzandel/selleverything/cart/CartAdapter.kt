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
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.R
import com.franzandel.selleverything.data.entity.CartMultiType
import com.franzandel.selleverything.data.enums.CartSection
import com.franzandel.selleverything.databinding.ItemCartContentBinding
import com.franzandel.selleverything.newest.Product

/**
 * Created by Franz Andel on 17/09/20.
 * Android Engineer
 */

class CartAdapter(private val context: Context) :
    ListAdapter<CartMultiType<Product>, RecyclerView.ViewHolder>(CartDiffCallback()) {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_CONTENT = 1
    }

    private lateinit var cartHeaderViewHolder: CartHeaderViewHolder
    private lateinit var cartContentViewHolder: CartContentViewHolder
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

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position].section) {
            CartSection.HEADER -> TYPE_HEADER
            CartSection.CONTENT -> TYPE_CONTENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)

        return when (viewType) {
            TYPE_HEADER -> {
                val view = layoutInflater.inflate(R.layout.item_cart_header, parent, false)
                cartHeaderViewHolder = CartHeaderViewHolder(view)
                setupCartHeaderObserver()
                cartHeaderViewHolder
            }
            else -> {
                val itemCartContentBinding: ItemCartContentBinding =
                    DataBindingUtil.inflate(
                        layoutInflater,
                        R.layout.item_cart_content,
                        parent,
                        false
                    )
                cartContentViewHolder = CartContentViewHolder(itemCartContentBinding)
                setupCartContentObserver()
                cartContentViewHolder
            }
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
        cartHeaderViewHolder.onCheckSellerClicked.observe(
            activity,
            Observer { (seller, isChecked) ->
                _onCheckSellerClicked.value = Triple(seller, isChecked, currentList)
            })
    }

    private fun setupCartContentObserver() {
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