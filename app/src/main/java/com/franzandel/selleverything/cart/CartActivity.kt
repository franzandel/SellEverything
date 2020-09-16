package com.franzandel.selleverything.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.franzandel.selleverything.R
import com.franzandel.selleverything.recyclerview.SellEverythingAdapter
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        ).get(CartVM::class.java)
    }

    private val adapter = SellEverythingAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        setupRV()
        setupObserver()
    }

    private fun setupRV() {
        rvCart.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.cartProducts.observe(this, Observer { products ->
            adapter.submitList(products)
        })
    }
}