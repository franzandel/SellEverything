package com.franzandel.selleverything.features.shipping.bottomsheet.courier.presentation

import android.content.DialogInterface
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.franzandel.selleverything.R
import com.franzandel.selleverything.base.presentation.BaseBottomSheet
import com.franzandel.selleverything.features.shipping.bottomsheet.data.entity.Courier
import kotlinx.android.synthetic.main.bs_courier.*

class CourierBs(private val couriers: List<Courier>) : BaseBottomSheet() {

    private val courierAdapter by lazy {
        CourierBsAdapter(requireContext(), couriers)
    }

    private val courierVM by lazy {
        ViewModelProvider(this).get(CourierBsVM::class.java)
    }

    private val _onClicked = MutableLiveData<Courier>()
    val onClicked: LiveData<Courier> = _onClicked

    private val _onCancelClicked = MutableLiveData<Unit>()
    val onCancelClicked: LiveData<Unit> = _onCancelClicked

    override fun getLayoutId(): Int = R.layout.bs_courier

    override fun onActivityReady() {
        setupRV()
        setupObserver()
        setupUIClickListener()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        _onCancelClicked.value = Unit
    }

    private fun setupObserver() {
        courierAdapter.onClicked.observe(viewLifecycleOwner, Observer { courier ->
            courierVM.resetCouriersIsChecked(couriers, courier)
            _onClicked.value = courier
            dismiss()
        })
    }

    private fun setupRV() {
        rvCourier.adapter = courierAdapter
    }

    private fun setupUIClickListener() {
        ivCourierClose.setOnClickListener {
            dismiss()
        }
    }
}