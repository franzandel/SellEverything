package com.franzandel.selleverything.base.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by Franz Andel on 10/10/20.
 * Android Engineer
 */

abstract class BaseBottomSheet : BottomSheetDialogFragment() {

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun onActivityReady()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(getLayoutId(), container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onActivityReady()
    }
}