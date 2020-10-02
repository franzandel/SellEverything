package com.franzandel.selleverything.paymentsuccess

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.franzandel.selleverything.HomeActivity
import com.franzandel.selleverything.R
import com.franzandel.selleverything.extension.goTo

class PaymentSuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_success)

        supportActionBar?.hide()
        closeActivityAfter2Seconds()
    }

    private fun closeActivityAfter2Seconds() {
        Handler().postDelayed({
            kotlin.run {
                goTo(HomeActivity::class.java)
                finishAffinity()
            }
        }, 2000L)
    }
}