package com.franzandel.selleverything.delivery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.franzandel.selleverything.R
import com.franzandel.selleverything.data.entity.Delivery
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_delivery_bottom_sheet.*

class DeliveryBottomSheet : BottomSheetDialogFragment() {

    private val deliveryBottomSheetAdapter by lazy {
        DeliveryBottomSheetAdapter(requireContext(), DeliveryList.deliveries)
    }

    private val _onClicked = MutableLiveData<Delivery>()
    val onClicked: LiveData<Delivery> = _onClicked

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.activity_delivery_bottom_sheet, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRV()
        setupObserver()
        setupUIClickListener()
    }

    private fun setupObserver() {
        deliveryBottomSheetAdapter.onClicked.observe(this, Observer { delivery ->
            _onClicked.value = delivery
            dismiss()
        })
    }

    private fun setupRV() {
        rvDelivery.adapter = deliveryBottomSheetAdapter
    }

    private fun setupUIClickListener() {
        ivDeliveryClose.setOnClickListener {
            dismiss()
        }
    }
}