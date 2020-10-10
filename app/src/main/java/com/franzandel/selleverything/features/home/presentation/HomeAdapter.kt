package com.franzandel.selleverything.features.home.presentation

import android.content.Context
import android.view.View
import android.widget.Filter
import android.widget.Filterable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.franzandel.selleverything.R
import com.franzandel.selleverything.base.presentation.BaseListAdapter
import com.franzandel.selleverything.data.entity.Product

/**
 * Created by Franz Andel on 17/08/20.
 * Android Engineer
 */

class HomeAdapter(context: Context) :
    BaseListAdapter<HomeViewHolder, Product>(context, { HomeDiffCallback() }), Filterable {

    private val homeFilter by lazy {
        HomeFilter()
    }
    private val activity = context as AppCompatActivity

    fun setFilterProducts() {
        homeFilter.setProducts(currentList)
    }

    fun setupObserver() {
        homeFilter.filteredProducts.observe(activity, Observer {
            submitList(it)
        })
    }

    override fun getItemLayoutId(): Int = R.layout.item_sell_everything

    override fun getViewHolder(view: View): HomeViewHolder = HomeViewHolder(view)

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val product = currentList[position]
        holder.bind(product)
    }

    override fun getFilter(): Filter = homeFilter
}