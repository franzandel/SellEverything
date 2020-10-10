package com.franzandel.selleverything.base.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Franz Andel on 10/10/20.
 * Android Engineer
 */

abstract class BaseAdapter<VH : RecyclerView.ViewHolder, DATA>(
    private val context: Context
) : RecyclerView.Adapter<VH>() {

    protected lateinit var viewHolder: VH
    protected var viewType = 0

    abstract fun getViewHolder(view: View): VH

    @LayoutRes
    abstract fun getItemLayoutId(): Int

    abstract fun getCurrentList(): List<DATA>

    open fun actionOnCreateViewHolder() {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        this.viewType = viewType
        val view = LayoutInflater.from(context).inflate(getItemLayoutId(), parent, false)
        viewHolder = getViewHolder(view)
        actionOnCreateViewHolder()
        return viewHolder
    }

    override fun getItemCount(): Int = getCurrentList().size
}