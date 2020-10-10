package com.franzandel.selleverything.features.paymentsuccess.presentation

import android.os.Handler
import com.franzandel.selleverything.R
import com.franzandel.selleverything.base.BaseActivity
import com.franzandel.selleverything.extension.goTo
import com.franzandel.selleverything.features.home.presentation.HomeActivity

class PaymentSuccessActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_payment_success

    override fun onActivityReady() {
        goToHomeAfter2Seconds()
    }

    private fun goToHomeAfter2Seconds() {
        Handler().postDelayed({
            kotlin.run {
                goTo(HomeActivity::class.java)
                finishAffinity()
            }
        }, 2000L)
    }
}