package com.franzandel.selleverything.features.payment.presentation

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.franzandel.selleverything.R
import com.franzandel.selleverything.base.presentation.BaseActivity
import com.franzandel.selleverything.data.constants.BundleConstants
import com.franzandel.selleverything.data.entity.ShippingSummary
import com.franzandel.selleverything.extension.getFormattedIDNPrice
import com.franzandel.selleverything.extension.goTo
import com.franzandel.selleverything.features.payment.bottomsheet.presentation.PaymentMethodBs
import com.franzandel.selleverything.features.payment.data.entity.PaymentMethod
import com.franzandel.selleverything.features.paymentsuccess.presentation.PaymentSuccessActivity
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : BaseActivity() {

    private val shippingSummary by lazy {
        intent.getParcelableExtra(BundleConstants.EXTRA_SHIPPING_SUMMARY)
            ?: ShippingSummary(
                totalQty = "0",
                totalPrice = "0"
            )
    }

    private val vm by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
            PaymentVM::class.java
        )
    }

    override fun getLayoutId(): Int = R.layout.activity_payment

    override fun onActivityReady() {
        setSupportActionBar(mtbPayment)
        setupUI()
        setupUIClickListener()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupUI() {
        ivPayment.setImageResource(R.drawable.ic_bri)
        val paymentMethod = getPaymentMethod()
        tvPaymentTitle.text = paymentMethod.name
        tvPaymentServicePrice.text = paymentMethod.servicePrice.getFormattedIDNPrice()

        val totalPaymentPriceWithoutServicePrice =
            vm.getTotalPaymentPriceWithoutServicePrice(shippingSummary)
        val totalPaymentPrice = vm.getTotalPaymentPrice(shippingSummary, paymentMethod)
        tvPaymentPrice.text = totalPaymentPriceWithoutServicePrice.getFormattedIDNPrice()
        tvPaymentTotalPrice.text = totalPaymentPrice.getFormattedIDNPrice()
    }

    private fun getPaymentMethod(): PaymentMethod =
        PaymentMethod(
            type = "",
            name = getString(R.string.payment_direct_debit_bri_title),
            servicePrice = getString(R.string.payment_direct_debit_bri_service_price).toInt()
        )

    private fun setupUIClickListener() {
        tvPaymentSeeAll.setOnClickListener {
            val paymentMethodBs = PaymentMethodBs()
            setupPaymentMethodBsObserver(paymentMethodBs)
            paymentMethodBs.show(supportFragmentManager, paymentMethodBs.tag)
        }

        btnPaymentPay.setOnClickListener {
            vm.deleteAllFromCart()
            goTo(PaymentSuccessActivity::class.java)
        }
    }

    private fun setupPaymentMethodBsObserver(paymentMethodBs: PaymentMethodBs) {
        paymentMethodBs.onPaymentContentClicked.observe(this, Observer { paymentMethod ->
            val totalPaymentPrice = vm.getTotalPaymentPrice(shippingSummary, paymentMethod)
            ivPayment.setImageResource(paymentMethod.icon)
            tvPaymentTitle.text = paymentMethod.name
            tvPaymentServicePrice.text = paymentMethod.servicePrice.getFormattedIDNPrice()
            tvPaymentTotalPrice.text = totalPaymentPrice.getFormattedIDNPrice()
        })
    }
}