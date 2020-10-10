package com.franzandel.selleverything.features.cart.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.franzandel.selleverything.R
import com.franzandel.selleverything.data.constants.BundleConstants
import com.franzandel.selleverything.data.constants.NumberConstants
import com.franzandel.selleverything.data.entity.Product
import com.franzandel.selleverything.extension.*
import com.franzandel.selleverything.features.cart.data.entity.CartMultiType
import com.franzandel.selleverything.features.home.presentation.HomeActivity
import com.franzandel.selleverything.features.shipping.presentation.ShippingActivity
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
    private lateinit var multiTypeProducts: List<CartMultiType<Product>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        setSupportActionBar(mtbCart)
        setupRV()
        setupObserver()
        setupUIClickListener()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupRV() {
        rvCart.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.cartProducts.observe(this, Observer { products ->
            if (products.isEmpty()) {
                showEmptyCart()
            } else {
                setupUIWhenProductsIsNotEmpty(products)
                viewModel.processMultiTypeProducts(products)
            }
        })

        viewModel.multiTypeProducts.observe(this, Observer { multiTypeProducts ->
            this.multiTypeProducts = multiTypeProducts
            adapter.submitList(multiTypeProducts)
        })

        adapter.onCheckSellerClicked.observe(
            this,
            Observer { (seller, isChecked, multiTypeProducts) ->
                viewModel.checkAllProductsPerSeller(seller, isChecked, multiTypeProducts)
                setupCbCheckAll(multiTypeProducts)
                adapter.notifyDataSetChanged()
            })

        adapter.onCheckProductClicked.observe(this, Observer { (multiTypeProducts, seller) ->
            val totalCheckedProductsCount =
                viewModel.getTotalCheckedProductsCount(multiTypeProducts)

            if (totalCheckedProductsCount == NumberConstants.ZERO) {
                setupUIWhenTotalCheckedProductsCountIs0()
            } else {
                setupUIWhenTotalCheckedProductsCountIsNot0(
                    totalCheckedProductsCount,
                    multiTypeProducts,
                    seller
                )
            }

            btnCartBuy.text =
                getString(
                    R.string.cart_buy,
                    viewModel.getTotalCheckedProductsQty(multiTypeProducts)
                )
            setupCbCheckAll(multiTypeProducts, totalCheckedProductsCount)
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
            if (products.size == 1 && product.data.currentQty == 0) {
                setupUIWhenSizeIs1AndCurrentQtyIs0()
            } else {
                setupUIWhenSizeIsNot1AndCurrentQtyIsNot0(products)
            }

            btnCartBuy.text =
                getString(R.string.cart_buy, viewModel.getTotalCheckedProductsQty(products))
        })

        adapter.onDeleteClicked.observe(this, Observer { product ->
            viewModel.deleteFromCart(product)
        })

        viewModel.onCheckAllProducts.observe(this, Observer {
            adapter.notifyDataSetChanged()
        })
    }

    private fun setupUIWhenProductsIsNotEmpty(products: List<Product>) {
        cbCartCheckAll.text = getString(
            R.string.cart_check_all_with_number,
            viewModel.getTotalCheckedProductsCount(products)
        )
        btnCartBuy.text =
            getString(R.string.cart_buy, viewModel.getTotalCheckedProductsQty(products))
        tvCartTotalPrice.text =
            viewModel.getTotalCheckedProductsPrice(products).getFormattedIDNPrice()
    }

    private fun setupUIWhenSizeIs1AndCurrentQtyIs0() {
        btnCartBuy.backgroundTintList =
            AppCompatResources.getColorStateList(this, R.color.colorGray)
        btnCartBuy.isEnabled = false
        tvCartTotalPrice.text = NumberConstants.DASH
        tvCartTotalPrice.setTextColor(toColor(android.R.color.black))
    }

    private fun setupUIWhenSizeIsNot1AndCurrentQtyIsNot0(products: List<CartMultiType<Product>>) {
        btnCartBuy.backgroundTintList =
            AppCompatResources.getColorStateList(this, R.color.colorOrange)
        btnCartBuy.isEnabled = true
        tvCartTotalPrice.text =
            viewModel.getTotalCheckedProductsPrice(products).getFormattedIDNPrice()
        tvCartTotalPrice.setTextColor(toColor(R.color.colorOrange))
    }

    private fun setupCbCheckAll(
        multiTypeProducts: List<CartMultiType<Product>>,
        totalCheckedProductsCount: String = ""
    ) {
        cbCartCheckAll.isChecked = if (totalCheckedProductsCount.isEmpty()) {
            viewModel.getTotalCheckedProductsCount(multiTypeProducts) ==
                    viewModel.getTotalProductsCount(multiTypeProducts)
        } else {
            totalCheckedProductsCount ==
                    viewModel.getTotalProductsCount(multiTypeProducts)
        }
    }

    private fun setupUIWhenTotalCheckedProductsCountIs0() {
        cbCartCheckAll.text = getString(R.string.cart_check_all_no_number)
        btnCartBuy.backgroundTintList =
            AppCompatResources.getColorStateList(this, R.color.colorGray)
        btnCartBuy.isEnabled = false
        tvCartDeleteAll.hide()
        tvCartTotalPrice.text = NumberConstants.DASH
        tvCartTotalPrice.setTextColor(toColor(android.R.color.black))
    }

    private fun setupUIWhenTotalCheckedProductsCountIsNot0(
        totalCheckedProductsCount: String,
        multiTypeProducts: List<CartMultiType<Product>>,
        seller: String
    ) {
        setupCartHeader(multiTypeProducts, seller)

        cbCartCheckAll.text = getString(
            R.string.cart_check_all_with_number,
            totalCheckedProductsCount
        )
        btnCartBuy.backgroundTintList =
            AppCompatResources.getColorStateList(this, R.color.colorOrange)
        btnCartBuy.isEnabled = true
        tvCartDeleteAll.show()
        tvCartTotalPrice.text =
            viewModel.getTotalCheckedProductsPrice(multiTypeProducts).getFormattedIDNPrice()
        tvCartTotalPrice.setTextColor(toColor(R.color.colorOrange))
    }

    private fun setupCartHeader(
        multiTypeProducts: List<CartMultiType<Product>>,
        seller: String
    ) {
        val totalProductsCounterPerSeller =
            viewModel.getTotalProductsCountPerSeller(seller, multiTypeProducts)
        val totalProductsCheckedCountPerSeller =
            viewModel.getTotalProductsCheckedCountPerSeller(seller, multiTypeProducts)

        if (totalProductsCounterPerSeller == totalProductsCheckedCountPerSeller) {
            viewModel.checkProductsHeader(seller, multiTypeProducts, true)
        } else {
            viewModel.checkProductsHeader(seller, multiTypeProducts, false)
        }
        adapter.notifyDataSetChanged()
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
            viewModel.checkAllProducts(multiTypeProducts, isChecked)
            if (isChecked) {
                cbCartCheckAll.text = getString(
                    R.string.cart_check_all_with_number,
                    viewModel.getTotalCheckedProductsCount(multiTypeProducts)
                )
                tvCartDeleteAll.show()
            } else {
                cbCartCheckAll.text = getString(R.string.cart_check_all_no_number)
                tvCartDeleteAll.hide()
            }
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