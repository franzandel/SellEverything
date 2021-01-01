package com.franzandel.selleverything.features.home.presentation

import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.franzandel.selleverything.R
import com.franzandel.selleverything.base.presentation.BaseActivity
import com.franzandel.selleverything.data.constants.NumberConstants
import com.franzandel.selleverything.data.entity.Product
import com.franzandel.selleverything.extension.goTo
import com.franzandel.selleverything.extension.hide
import com.franzandel.selleverything.extension.show
import com.franzandel.selleverything.features.cart.presentation.CartActivity
import com.franzandel.selleverything.features.home.searchview.SearchViewTextListener
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar_with_search_view.*

class HomeActivity : BaseActivity() {

    private val adapter = HomeAdapter(this)
    private var tvCartItemCount: TextView? = null

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        ).get(HomeVM::class.java)
    }

    override fun getLayoutId(): Int = R.layout.activity_home

    override fun onActivityReady() {
        setupToolbar()
        setupRV()
        setupObserver()
        setupUIClickListener()
        viewModel.getProducts()
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

        val cartMenuItem = menu?.findItem(R.id.menu_cart)
        val cartActionView = cartMenuItem?.actionView
        tvCartItemCount = cartActionView?.findViewById(R.id.cart_badge)

        cartActionView?.setOnClickListener {
            onOptionsItemSelected(cartMenuItem)
        }

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

    private fun setupObserver() {
        viewModel.cartProducts.observe(this, Observer { products ->
            setupBadge(products)
        })

        viewModel.productsResult.observe(this, Observer { products ->
            adapter.apply {
                setupObserver()
                submitList(products)
                setFilterProducts()
            }
        })
    }

    private fun setupBadge(products: List<Product>) {
        if (tvCartItemCount != null) {
            val cartItemCount = viewModel.getTotalCheckedProductsQty(products)
            if (cartItemCount == NumberConstants.ZERO) {
                tvCartItemCount?.hide()
            } else {
                tvCartItemCount?.text = cartItemCount
                tvCartItemCount?.show()
            }
        }
    }

    private fun setupRV() {
        rvSellEverything.adapter = adapter
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