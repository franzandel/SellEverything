package com.franzandel.selleverything.courier

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.franzandel.selleverything.R
import com.franzandel.selleverything.data.entity.Courier
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_courier_bottom_sheet.*

class CourierBottomSheet(private val couriers: List<Courier>) : BottomSheetDialogFragment() {

    private val courierAdapter by lazy {
        CourierAdapter(requireContext(), couriers)
    }

    private val courierVM by lazy {
        ViewModelProvider(this).get(CourierVM::class.java)
    }

    private val _onClicked = MutableLiveData<Courier>()
    val onClicked: LiveData<Courier> = _onClicked

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.activity_courier_bottom_sheet, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRV()
        setupObserver()
        setupUIClickListener()
    }

    private fun setupObserver() {
        courierAdapter.onClicked.observe(this, Observer { courier ->
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