package com.franzandel.selleverything.shipping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.franzandel.selleverything.R
import com.franzandel.selleverything.data.constants.BundleConstants
import com.franzandel.selleverything.newest.Product
import kotlinx.android.synthetic.main.activity_shipping.*

class ShippingActivity : AppCompatActivity() {

    private val products by lazy {
        intent.getParcelableArrayListExtra<Product>(BundleConstants.EXTRA_PRODUCTS)
    }

    private val shippingVM by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
            ShippingVM::class.java
        )
    }

    private val shippingAdapter = ShippingAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipping)

        setupToolbar()
        setupRV()
        setupObserver()
        shippingVM.processMultiTypeProducts(products)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupToolbar() {
        supportActionBar?.title = getString(R.string.shipping_toolbar_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupRV() {
        rvShipping.adapter = shippingAdapter
    }

    private fun setupObserver() {
        shippingVM.multiTypeProducts.observe(this, Observer { multiTypeProducts ->
            shippingAdapter.setData(multiTypeProducts)
        })

        shippingAdapter.onDeliveryOrCourierClicked.observe(
            this,
            Observer { (multiTypeProducts, courierPrice) ->
                shippingVM.addTotalOrderPriceProductSummary(
                    products,
                    multiTypeProducts,
                    courierPrice
                )
            })

        shippingAdapter.onDeliveryOrCourierFirstClicked.observe(
            this,
            Observer { (multiTypeProducts, courierPrice) ->
                shippingVM.minusTotalOrderPriceProductSummary(
                    products,
                    multiTypeProducts,
                    courierPrice
                )
            })

        shippingVM.multiTypeProducts2.observe(this, Observer { multiTypeProducts ->
            shippingAdapter.setData(multiTypeProducts)
            // TODO: CHECK BELOW CODE IS USED OR NOT
            shippingAdapter.notifyItemChanged(multiTypeProducts.size - 1)
        })
    }
}
