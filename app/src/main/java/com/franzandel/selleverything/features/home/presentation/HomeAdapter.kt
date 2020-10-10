package com.franzandel.selleverything.features.home.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ListAdapter
import com.franzandel.selleverything.R
import com.franzandel.selleverything.data.entity.Product

/**
 * Created by Franz Andel on 17/08/20.
 * Android Engineer
 */

class HomeAdapter(private val context: Context) :
    ListAdapter<Product, HomeViewHolder>(HomeDiffCallback()), Filterable {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_sell_everything, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val product = currentList[position]
        holder.bind(product)
    }

    override fun getFilter(): Filter = homeFilter
}