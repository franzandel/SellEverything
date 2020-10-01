package com.franzandel.selleverything.payment

import com.franzandel.selleverything.data.entity.PaymentMethod

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
        PaymentMethod(debit, "Direct Debit BRI", 5000),
        PaymentMethod(debit, "BCA OneKlik", 7000),
        PaymentMethod(debit, "Direct Debit Mandiri", 10000)
    )

    private val paymentCCMethods = listOf(
        PaymentMethod(cc, "Kartu Kredit / Debit", 3000)
    )

    private val paymentVAMethods = listOf(
        PaymentMethod(transferVA, "Mandiri Virtual Account", 3000),
        PaymentMethod(transferVA, "BCA Virtual Account", 4000),
        PaymentMethod(transferVA, "BRIVA", 5000),
        PaymentMethod(transferVA, "BNI Virtual Account", 6000),
        PaymentMethod(transferVA, "BTN Virtual Account", 7000),
        PaymentMethod(transferVA, "Danamon Virtual Account", 8000),
        PaymentMethod(transferVA, "CIMB Virtual Account", 9000),
        PaymentMethod(transferVA, "Virtual Account Lainnya", 10000)
    )

    private val paymentVASyariahMethods = listOf(
        PaymentMethod(transferVASyariah, "Bank Syariah Mandiri", 0),
        PaymentMethod(transferVASyariah, "BNISyariah", 0),
        PaymentMethod(transferVASyariah, "Bank Muamalat", 0)
    )

    private val paymentBankMethods = listOf(
        PaymentMethod(transferBank, "Transfer Bank BCA", 1000),
        PaymentMethod(transferBank, "Transfer Bank Mandiri", 2000),
        PaymentMethod(transferBank, "Transfer Bank BNI", 3000),
        PaymentMethod(transferBank, "Transfer Bank BRI", 4000),
        PaymentMethod(transferBank, "Transfer Bank CIMB", 5000)
    )

    private val paymentInstantMethods = listOf(
        PaymentMethod(instant, "KlikBCA", 0),
        PaymentMethod(instant, "BCA KlikPay", 0),
        PaymentMethod(instant, "BRI e-Pay", 0),
        PaymentMethod(instant, "Jenius Pay", 0),
        PaymentMethod(instant, "JakOne Mobile Bank DKI", 0),
        PaymentMethod(instant, "OCTO Clicks", 0),
        PaymentMethod(instant, "LinkAja", 0)
    )

    private val paymentInstallmentMethods = listOf(
        PaymentMethod(installment, "OVO PayLater", 0),
        PaymentMethod(installment, "BRI Ceria", 0),
        PaymentMethod(installment, "Kredivo", 0),
        PaymentMethod(installment, "Home Credit", 0)
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