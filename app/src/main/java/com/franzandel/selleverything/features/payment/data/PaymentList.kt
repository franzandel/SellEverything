package com.franzandel.selleverything.features.payment.data

import com.franzandel.selleverything.R
import com.franzandel.selleverything.features.payment.data.entity.PaymentMethod

/**
 * Created by Franz Andel on 01/10/20.
 * Android Engineer
 */

object PaymentList {
    private const val debit = "Debit Instan"
    private const val cc = "Kartu Kredit"
    private const val transferVA = "Transfer Virtual Account"
    private const val transferVASyariah = "Transfer Virtual Account Syariah"
    private const val transferBank = "Transfer Bank (Verifikasi Manual)"
    private const val instant = "Pembayaran Instan"
    private const val installment = "Cicilan Tanpa Kartu Kredit"

    private val paymentDebitMethods = listOf(
        PaymentMethod(debit, "Direct Debit BRI", 5000, R.drawable.ic_bri),
        PaymentMethod(debit, "BCA OneKlik", 7000, R.drawable.ic_bca),
        PaymentMethod(debit, "Direct Debit Mandiri", 10000, R.drawable.ic_mandiri)
    )

    private val paymentCCMethods = listOf(
        PaymentMethod(cc, "Kartu Kredit / Debit", 3000, R.drawable.ic_credit_card)
    )

    private val paymentVAMethods = listOf(
        PaymentMethod(transferVA, "Mandiri Virtual Account", 3000, R.drawable.ic_mandiri),
        PaymentMethod(transferVA, "BCA Virtual Account", 4000, R.drawable.ic_bca),
        PaymentMethod(transferVA, "BRIVA", 5000, R.drawable.ic_bri),
        PaymentMethod(transferVA, "BNI Virtual Account", 6000, R.drawable.ic_bni),
        PaymentMethod(transferVA, "BTN Virtual Account", 7000, R.drawable.ic_btn),
        PaymentMethod(transferVA, "Permata Virtual Account", 8000, R.drawable.ic_permata),
        PaymentMethod(transferVA, "CIMB Virtual Account", 9000, R.drawable.ic_cimb_niaga),
        PaymentMethod(transferVA, "Virtual Account Lainnya", 10000, R.drawable.ic_visa)
    )

    private val paymentVASyariahMethods = listOf(
        PaymentMethod(transferVASyariah, "Bank Syariah Mandiri", 0, R.drawable.ic_mandiri),
        PaymentMethod(transferVASyariah, "BNISyariah", 0, R.drawable.ic_bni),
        PaymentMethod(transferVASyariah, "Bank Muamalat", 0, R.drawable.ic_bank_muamalat)
    )

    private val paymentBankMethods = listOf(
        PaymentMethod(transferBank, "Transfer Bank BCA", 1000, R.drawable.ic_bca),
        PaymentMethod(transferBank, "Transfer Bank Mandiri", 2000, R.drawable.ic_mandiri),
        PaymentMethod(transferBank, "Transfer Bank BNI", 3000, R.drawable.ic_bni),
        PaymentMethod(transferBank, "Transfer Bank BRI", 4000, R.drawable.ic_bri),
        PaymentMethod(transferBank, "Transfer Bank CIMB", 5000, R.drawable.ic_cimb_niaga)
    )

    private val paymentInstantMethods = listOf(
        PaymentMethod(instant, "KlikBCA", 0, R.drawable.ic_bca),
        PaymentMethod(instant, "BCA KlikPay", 0, R.drawable.ic_bca),
        PaymentMethod(instant, "BRI e-Pay", 0, R.drawable.ic_bri),
        PaymentMethod(instant, "BJB Pay", 0, R.drawable.ic_bjb),
        PaymentMethod(instant, "UOB Mobile Bank", 0, R.drawable.ic_uob),
        PaymentMethod(instant, "HSBC Clicks", 0, R.drawable.ic_hsbc)
    )

    private val paymentInstallmentMethods = listOf(
        PaymentMethod(installment, "BRI Ceria", 0, R.drawable.ic_bri),
        PaymentMethod(installment, "Bukopin", 0, R.drawable.ic_bukopin)
    )

    fun getPaymentMethods(): List<PaymentMethod> {
        return mutableListOf<PaymentMethod>().apply {
            addAll(paymentDebitMethods)
            addAll(paymentCCMethods)
            addAll(paymentVAMethods)
            addAll(paymentVASyariahMethods)
            addAll(paymentBankMethods)
            addAll(paymentInstantMethods)
            addAll(paymentInstallmentMethods)
        }
    }
}