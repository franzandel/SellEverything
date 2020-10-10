package com.franzandel.selleverything.features.shipping.bottomsheet.delivery.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.franzandel.selleverything.R
import com.franzandel.selleverything.base.BaseBottomSheet
import com.franzandel.selleverything.features.shipping.bottomsheet.delivery.data.DeliveryList
import com.franzandel.selleverything.features.shipping.bottomsheet.delivery.data.entity.Delivery
import kotlinx.android.synthetic.main.bs_delivery.*

class DeliveryBs : BaseBottomSheet() {

    private val deliveryBottomSheetAdapter by lazy {
        DeliveryBsAdapter(requireContext(), DeliveryList.deliveries)
    }

    private val _onClicked = MutableLiveData<Delivery>()
    val onClicked: LiveData<Delivery> = _onClicked

    override fun getLayoutId(): Int = R.layout.bs_delivery

    override fun onActivityReady() {
        setupRV()
        setupObserver()
        setupUIClickListener()
    }

    private fun setupObserver() {
        deliveryBottomSheetAdapter.onClicked.observe(viewLifecycleOwner, Observer { delivery ->
            _onClicked.value = delivery
            dismiss()
        })
    }

    private fun setupRV() {
        rvPaymentMethod.adapter = deliveryBottomSheetAdapter
    }

    private fun setupUIClickListener() {
        ivPaymentMethodClose.setOnClickListener {
            dismiss()
        }
    }
}