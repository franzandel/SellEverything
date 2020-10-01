package com.franzandel.selleverything.payment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.franzandel.selleverything.R
import com.franzandel.selleverything.data.constants.BundleConstants
import com.franzandel.selleverything.data.entity.ShippingSummary
import com.franzandel.selleverything.extension.getFormattedIDNPrice
import com.franzandel.selleverything.extension.showToast
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : AppCompatActivity() {

    private val shippingSummary by lazy {
        intent.getParcelableExtra(BundleConstants.EXTRA_SHIPPING_SUMMARY)
            ?: ShippingSummary(
                totalQty = "0",
                totalPrice = "0"
            )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        setupToolbar()
        setupUI()
        setupUIClickListener()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupToolbar() {
        supportActionBar?.title = getString(R.string.payment_toolbar_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupUI() {
        val paymentServicePrice =
            getString(R.string.payment_direct_debit_bri_service_price).toLong()
        tvPaymentTitle.text = getString(R.string.payment_direct_debit_bri_title)
        tvPaymentServicePrice.text = paymentServicePrice.getFormattedIDNPrice()

        val totalPaymentPrice = getTotalPaymentPrice()
        val newTotalPaymentPrice = totalPaymentPrice + paymentServicePrice
        tvPaymentPrice.text = totalPaymentPrice.getFormattedIDNPrice()
        tvPaymentTotalPrice.text = newTotalPaymentPrice.getFormattedIDNPrice()
    }

    private fun setupUIClickListener() {
        tvPaymentSeeAll.setOnClickListener {
            val paymentMethodBottomSheet = PaymentMethodBs()
            setupPaymentMethodBottomSheetObserver(paymentMethodBottomSheet)
            paymentMethodBottomSheet.show(supportFragmentManager, paymentMethodBottomSheet.tag)
        }

        btnPaymentPay.setOnClickListener {
            showToast("Pay all")
        }
    }

    private fun setupPaymentMethodBottomSheetObserver(paymentMethodBs: PaymentMethodBs) {
        paymentMethodBs.onPaymentContentClicked.observe(this, Observer { paymentMethod ->
            val totalPaymentPrice = getTotalPaymentPrice()
            val servicePrice = paymentMethod.servicePrice
            val newTotalPaymentPrice = totalPaymentPrice + servicePrice
            tvPaymentTitle.text = paymentMethod.name
            tvPaymentServicePrice.text = paymentMethod.servicePrice.getFormattedIDNPrice()
            tvPaymentTotalPrice.text = newTotalPaymentPrice.getFormattedIDNPrice()
        })
    }

    private fun getTotalPaymentPrice(): Long {
        val totalPrice = shippingSummary.totalPrice.toLong()
        val totalShippingPrice = shippingSummary.totalShippingPrice.toLong()
        return totalPrice + totalShippingPrice
    }
}