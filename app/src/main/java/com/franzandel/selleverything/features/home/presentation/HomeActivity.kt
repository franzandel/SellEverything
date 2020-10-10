package com.franzandel.selleverything.features.home.presentation

import android.view.Menu
import android.view.MenuItem
import com.franzandel.selleverything.R
import com.franzandel.selleverything.base.BaseActivity
import com.franzandel.selleverything.data.entity.Products
import com.franzandel.selleverything.extension.goTo
import com.franzandel.selleverything.features.cart.presentation.CartActivity
import com.franzandel.selleverything.features.home.searchview.SearchViewTextListener
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar_with_search_view.*
import java.io.IOException


class HomeActivity : BaseActivity() {

    private val adapter = HomeAdapter(this)

    override fun getLayoutId(): Int = R.layout.activity_home

    override fun onActivityReady() {
        setupToolbar()
        setupRV()
        setupRVData()
        setupUIClickListener()
    }

    override fun onBackPressed() {
        if (materialSearchView.isSearchOpen) {
            materialSearchView.closeSearch()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)

        val searchMenuItem = menu?.findItem(R.id.menu_search)
        materialSearchView.setMenuItem(searchMenuItem)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_cart -> goTo(CartActivity::class.java)
        }
        return true
    }

    private fun setupToolbar() {
        setSupportActionBar(materialToolbar)
        supportActionBar?.title = getString(R.string.home_toolbar_title)
    }

    private fun setupRV() {
        rvSellEverything.adapter = adapter
    }

    private fun setupRVData() {
        val jsonString = readJSONFromRawResource()
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(Products::class.java)
        val newProduct = jsonAdapter.fromJson(jsonString)

        adapter.apply {
            setupObserver()
            submitList(newProduct!!.products)
            setFilterProducts()
        }
    }

    private fun readJSONFromRawResource(): String {
        return try {
            val inputStream = resources.openRawResource(R.raw.products)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    private fun setupUIClickListener() {
        materialSearchView.setOnQueryTextListener(object : SearchViewTextListener() {
            override fun onQueryTextChange(typedText: String): Boolean {
                adapter.filter.filter(typedText)
                return false
            }
        })
    }
}