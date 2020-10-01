package com.franzandel.selleverything.shipping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.franzandel.selleverything.R
import com.franzandel.selleverything.data.constants.BundleConstants
import com.franzandel.selleverything.extension.goTo
import com.franzandel.selleverything.extension.showSnackbar
import com.franzandel.selleverything.newest.Product
import com.franzandel.selleverything.payment.PaymentActivity
import com.google.android.material.snackbar.Snackbar
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

        shippingAdapter.onDeliveryOrCourierBsSelected.observe(
            this,
            Observer { (multiTypeProducts, courierPrice) ->
                shippingVM.addTotalShippingPriceProductSummary(
                    products,
                    multiTypeProducts,
                    courierPrice
                )
                shippingAdapter.notifyDataSetChanged()
            })

        shippingAdapter.onDeliveryOrCourierBsOpened.observe(
            this,
            Observer { (multiTypeProducts, courierPrice) ->
                shippingVM.minusTotalShippingPriceProductSummary(
                    products,
                    multiTypeProducts,
                    courierPrice
                )
                shippingAdapter.notifyDataSetChanged()
            })

        shippingVM.editedSummaryProducts.observe(this, Observer { editedSummaryProducts ->
            shippingAdapter.setData(editedSummaryProducts)
            shippingAdapter.notifyItemChanged(editedSummaryProducts.size - 1)
        })

        shippingAdapter.onShippingChoosePaymentClicked.observe(
            this,
            Observer { shippingFooters ->
                shippingVM.validateShippingFooters(shippingFooters)
            })

        shippingVM.validateShippingFooter.observe(this, Observer { (adapterPosition, isAddress) ->
            rvShipping.smoothScrollToPosition(adapterPosition)
            isAddress?.let {
                val validationMessage = if (it) {
                    getString(R.string.shipping_address_validation_message)
                } else {
                    getString(R.string.shipping_delivery_validation_message)
                }
                showSnackbar(rvShipping, validationMessage, Snackbar.LENGTH_LONG)
            }
        })

        shippingVM.onValidateShippingFooterSucceed.observe(this, Observer { shippingSummary ->
            goTo(PaymentActivity::class.java) {
                putExtra(BundleConstants.EXTRA_SHIPPING_SUMMARY, shippingSummary)
            }
        })
    }
}
