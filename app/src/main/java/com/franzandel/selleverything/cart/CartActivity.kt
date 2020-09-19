package com.franzandel.selleverything.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.franzandel.selleverything.R
import com.franzandel.selleverything.extension.hide
import com.franzandel.selleverything.extension.show
import com.franzandel.selleverything.extension.showToast
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        ).get(CartVM::class.java)
    }

    private val adapter = CartAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        setupRV()
        setupObserver()
        setupUIClickListener()
    }

    private fun setupRV() {
        rvCart.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.cartProducts.observe(this, Observer { products ->
            cbCartCheckAll.text =
                getString(R.string.cart_check_all_with_number, products.size.toString())
            btnCartBuy.text = getString(R.string.cart_buy, products.size.toString())
            tvCartTotalPrice.text = viewModel.getTotalProductsPrice(products)
            adapter.submitList(products)
        })
    }

    private fun setupUIClickListener() {
        btnCartBuy.setOnClickListener {
            // TODO: MAKE NEW SHIPPING ACTIVITY
            showToast("Go to Shipping Activity")
        }

        cbCartCheckAll.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                tvCartDeleteAll.show()
                cbCartCheckAll.text = getString(R.string.cart_check_all_with_number, "15")
            } else {
                tvCartDeleteAll.hide()
                cbCartCheckAll.text = getString(R.string.cart_check_all_no_number)
            }
            adapter.checkAllProducts(isChecked)
        }

        tvCartDeleteAll.setOnClickListener {
            // TODO: DELETE ALL CART PRODUCTS
            showToast("Delete all cart product")
        }
    }
}