package com.franzandel.selleverything.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.franzandel.selleverything.R
import com.franzandel.selleverything.extension.hide
import com.franzandel.selleverything.extension.show
import com.franzandel.selleverything.extension.showToast
import com.franzandel.selleverything.extension.toColor
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        ).get(CartVM::class.java)
    }

    private val adapter = CartAdapter(this)
    private var isFirstLaunch = true

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
            if (isFirstLaunch) {
                cbCartCheckAll.text =
                    getString(
                        R.string.cart_check_all_with_number,
                        viewModel.getTotalCheckedProductsQty(products)
                    )
                btnCartBuy.text =
                    getString(R.string.cart_buy, viewModel.getTotalCheckedProductsQty(products))
                tvCartTotalPrice.text = viewModel.getTotalProductsPrice(products)
                adapter.submitList(products)
                isFirstLaunch = false
            }
        })

        adapter.onCheckClicked.observe(this, Observer { products ->
            val totalCheckedProductsCount = viewModel.getTotalCheckedProductsCount(products)
            if (totalCheckedProductsCount == "0") {
                cbCartCheckAll.text = getString(R.string.cart_check_all_no_number)
                btnCartBuy.backgroundTintList =
                    AppCompatResources.getColorStateList(this, R.color.colorGray)
                btnCartBuy.isEnabled = false
                tvCartDeleteAll.hide()
                tvCartTotalPrice.text = "-"
                tvCartTotalPrice.setTextColor(toColor(android.R.color.black))
            } else {
                cbCartCheckAll.text = getString(
                    R.string.cart_check_all_with_number,
                    totalCheckedProductsCount
                )
                btnCartBuy.backgroundTintList =
                    AppCompatResources.getColorStateList(this, R.color.colorOrange)
                btnCartBuy.isEnabled = true
                tvCartDeleteAll.show()
                tvCartTotalPrice.text = viewModel.getTotalCheckedProductsPrice(products)
                tvCartTotalPrice.setTextColor(toColor(R.color.colorOrange))
            }

            btnCartBuy.text =
                getString(R.string.cart_buy, viewModel.getTotalCheckedProductsQty(products))
            cbCartCheckAll.isChecked = totalCheckedProductsCount == products.size.toString()
        })

        adapter.onQtyMinusClicked.observe(this, Observer { product ->
            viewModel.updateCart(product)
            tvCartTotalPrice.text = viewModel.getTotalProductsPriceAfterMinusClicked(
                product,
                tvCartTotalPrice.text.toString()
            )
            btnCartBuy.text = getString(
                R.string.cart_buy,
                viewModel.getTotalCheckedProductsCountAfterMinusClicked(
                    btnCartBuy.text.toString()
                )
            )
        })

        adapter.onQtyPlusClicked.observe(this, Observer { product ->
            viewModel.updateCart(product)
            tvCartTotalPrice.text = viewModel.getTotalProductsPriceAfterPlusClicked(
                product,
                tvCartTotalPrice.text.toString()
            )
            btnCartBuy.text = getString(
                R.string.cart_buy,
                viewModel.getTotalCheckedProductsCountAfterPlusClicked(
                    btnCartBuy.text.toString()
                )
            )
        })
    }

    private fun setupUIClickListener() {
        btnCartBuy.setOnClickListener {
            // TODO: MAKE NEW SHIPPING ACTIVITY
            showToast("Go to Shipping Activity")
        }

        cbCartCheckAll.setOnClickListener {
            val isChecked = cbCartCheckAll.isChecked
            if (isChecked) {
                cbCartCheckAll.text = getString(R.string.cart_check_all_with_number)
                tvCartDeleteAll.show()
            } else {
                cbCartCheckAll.text = getString(R.string.cart_check_all_no_number)
                tvCartDeleteAll.hide()
            }
            adapter.checkAllProducts(isChecked)
        }

        tvCartDeleteAll.setOnClickListener {
            // TODO: DELETE ALL CART PRODUCTS
            showToast("Delete all cart product")
        }
    }
}