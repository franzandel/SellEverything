package com.franzandel.selleverything.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.franzandel.selleverything.HomeActivity
import com.franzandel.selleverything.R
import com.franzandel.selleverything.data.constants.BundleConstants
import com.franzandel.selleverything.data.constants.NumberConstants
import com.franzandel.selleverything.extension.goTo
import com.franzandel.selleverything.extension.hide
import com.franzandel.selleverything.extension.show
import com.franzandel.selleverything.extension.toColor
import com.franzandel.selleverything.newest.Product
import com.franzandel.selleverything.shipping.ShippingActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.layout_empty_cart.*

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

        setupToolbar()
        setupRV()
        setupObserver()
        setupUIClickListener()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.cart_toolbar_title)
    }

    private fun setupRV() {
        rvCart.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.cartProducts.observe(this, Observer { products ->
            if (products.isEmpty()) {
                showEmptyCart()
            } else {
                cbCartCheckAll.text =
                    getString(
                        R.string.cart_check_all_with_number,
                        viewModel.getTotalCheckedProductsCount(products)
                    )
                btnCartBuy.text =
                    getString(R.string.cart_buy, viewModel.getTotalCheckedProductsQty(products))
                tvCartTotalPrice.text = viewModel.getTotalCheckedProductsPrice(products)
                adapter.submitList(products)
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
                tvCartTotalPrice.text = NumberConstants.DASH
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

        adapter.onQtyChanged.observe(this, Observer { (product, products) ->
            if (products.size == 1 && product.currentQty == 0) {
                btnCartBuy.backgroundTintList =
                    AppCompatResources.getColorStateList(this, R.color.colorGray)
                btnCartBuy.isEnabled = false
                tvCartTotalPrice.text = NumberConstants.DASH
                tvCartTotalPrice.setTextColor(toColor(android.R.color.black))
            } else {
                btnCartBuy.backgroundTintList =
                    AppCompatResources.getColorStateList(this, R.color.colorOrange)
                btnCartBuy.isEnabled = true
                tvCartTotalPrice.text = viewModel.getTotalCheckedProductsPrice(products)
                tvCartTotalPrice.setTextColor(toColor(R.color.colorOrange))
            }

            btnCartBuy.text =
                getString(R.string.cart_buy, viewModel.getTotalCheckedProductsQty(products))
        })

        adapter.onDeleteClicked.observe(this, Observer { product ->
            viewModel.deleteFromCart(product)
        })
    }

    private fun setupUIClickListener() {
        btnEmptyCartStartBuying.setOnClickListener {
            goTo(HomeActivity::class.java)
            finishAffinity()
        }

        btnCartBuy.setOnClickListener {
            goTo(ShippingActivity::class.java) {
                viewModel.cartProducts.value?.let { products ->
                    val productsArrayList = ArrayList<Product>()
                    productsArrayList.addAll(products)
                    putParcelableArrayListExtra(BundleConstants.EXTRA_PRODUCTS, productsArrayList)
                }
            }
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
            viewModel.cartProducts.value?.let { products ->
                MaterialAlertDialogBuilder(this)
                    .setTitle(
                        getString(
                            R.string.cart_delete_all_confirmation_title,
                            products.size.toString()
                        )
                    )
                    .setMessage(getString(R.string.cart_delete_all_confirmation_description))
                    .setNegativeButton(getString(R.string.delete_confirmation_negative_btn)) { _, _ -> }
                    .setPositiveButton(getString(R.string.delete_confirmation_positive_btn)) { _, _ ->
                        viewModel.deleteAllFromCart(products)
                        showEmptyCart()
                    }
                    .show()
            }
        }
    }

    private fun showEmptyCart() {
        emptyCartLayout.show()
        cbCartCheckAll.hide()
        tvCartDeleteAll.hide()
        vShadowUpper.hide()
        rvCart.hide()
        vShadowLower.hide()
        tvCartTotalPriceTitle.hide()
        tvCartTotalPrice.hide()
        btnCartBuy.hide()
    }
}