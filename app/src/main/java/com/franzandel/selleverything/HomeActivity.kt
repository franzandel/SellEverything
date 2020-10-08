package com.franzandel.selleverything

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.franzandel.selleverything.cart.CartActivity
import com.franzandel.selleverything.extension.goTo
import com.franzandel.selleverything.extension.showToast
import com.franzandel.selleverything.newest.NewProduct
import com.franzandel.selleverything.recyclerview.SellEverythingAdapter
import com.franzandel.selleverything.searchview.SearchViewTextListener
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar_with_search_view.*
import java.io.IOException


class HomeActivity : AppCompatActivity() {

    private val adapter = SellEverythingAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

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
        val jsonAdapter = moshi.adapter(NewProduct::class.java)
        val newProduct = jsonAdapter.fromJson(jsonString)
        Log.d("1234", newProduct.toString())

        adapter.submitList(newProduct!!.products)
    }

    private fun readJSONFromRawResource(): String {
        return try {
            val inputStream = resources.openRawResource(R.raw.newproductjson)
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
//                adapter.filter.filter(typedText)
                showToast(typedText)
                return false
            }
        })
    }
}