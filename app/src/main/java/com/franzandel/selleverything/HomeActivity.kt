package com.franzandel.selleverything

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.franzandel.selleverything.cart.CartActivity
import com.franzandel.selleverything.extension.goTo
import com.franzandel.selleverything.newest.NewProduct
import com.franzandel.selleverything.recyclerview.SellEverythingAdapter
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_home.*
import java.io.IOException

class HomeActivity : AppCompatActivity() {

    private val adapter = SellEverythingAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupRV()
        setupRVData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_cart -> goTo(CartActivity::class.java)
        }
        return true
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
}