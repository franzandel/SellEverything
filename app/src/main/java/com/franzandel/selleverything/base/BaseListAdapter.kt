package com.franzandel.selleverything.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Franz Andel on 10/10/20.
 * Android Engineer
 */

abstract class BaseListAdapter<VH : RecyclerView.ViewHolder, DATA>(
    protected val context: Context,
    diffUtilItemCallback: () -> DiffUtil.ItemCallback<DATA>
) : ListAdapter<DATA, VH>(diffUtilItemCallback.invoke()) {

    protected lateinit var viewHolder: VH
    protected var viewType = 0
    private var isDataBindingNeeded = false

    abstract fun getViewHolder(view: View): VH

    @LayoutRes
    abstract fun getItemLayoutId(): Int

    open fun actionOnCreateViewHolder() {}

    private fun isDataBindingNeeded(): Boolean = isDataBindingNeeded

    protected fun setIsDataBindingNeeded(isDataBindingNeeded: Boolean) {
        this.isDataBindingNeeded = isDataBindingNeeded
    }

    open fun getViewHolder(viewDataBinding: ViewDataBinding): VH =
        getViewHolder(viewDataBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        this.viewType = viewType

        val layoutInflater = LayoutInflater.from(context)
        viewHolder = if (isDataBindingNeeded()) {
            val viewDataBinding: ViewDataBinding =
                DataBindingUtil.inflate(
                    layoutInflater,
                    getItemLayoutId(),
                    parent,
                    false
                )
            getViewHolder(viewDataBinding)
        } else {
            val view = layoutInflater.inflate(getItemLayoutId(), parent, false)
            getViewHolder(view)
        }

        actionOnCreateViewHolder()
        return viewHolder
    }
}