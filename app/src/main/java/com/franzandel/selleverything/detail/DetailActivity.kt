package com.franzandel.selleverything.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.franzandel.selleverything.R
import com.franzandel.selleverything.cart.CartActivity
import com.franzandel.selleverything.data.BundleConstants
import com.franzandel.selleverything.extension.*
import com.franzandel.selleverything.newest.Product
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private val product by lazy {
        intent.extras?.getParcelable(BundleConstants.EXTRA_PRODUCT) ?: Product(
            id = "",
            cashback = "",
            discountPercentage = "",
            imageName = "",
            location = "",
            price = "",
            quantity = "",
            title = "",
            weight = "",
            condition = "",
            minOrder = "",
            category = "",
            description = ""
        )
    }

    private val viewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
            DetailVM::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setupUI()
        setupUIClickListener()
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

    private fun setupUI() {
        setupViewPager2()
        setupTabLayout()

        tvDetailDiscountedPrice.text =
            product.price.getDiscountedPrice(product.discountPercentage).toInt()
                .getFormattedIDNPrice()
        tvDetailCashbackPercentage.text = product.cashback.addCashbackPercentage()
        tvDetailDiscountPercentage.text = product.discountPercentage.addPercentage()
        tvDetailOriginalPrice.text = product.price.toInt().getFormattedIDNPrice()
        tvDetailOriginalPrice.showStrikeThrough()
        tvDetailTitle.text = product.title
        tvDetailWeight.text = product.weight.toInt().getFormattedWeight()
        tvDetailCondition.text = product.condition
        tvDetailMinOrder.text = product.minOrder.addUnit()
        tvDetailCategory.text = product.category
        tvDetailDescription.text = product.description
    }

    private fun setupViewPager2() {
        val productImages = listOf(product.imageName)
        vpDetailProductImage.adapter = DetailAdapter(this, productImages)
    }

    private fun setupTabLayout() {
        // To connect ViewPager2 with TabLayout
        TabLayoutMediator(tlDetailProductImage, vpDetailProductImage) { _, _ -> }.attach()
    }

    private fun setupUIClickListener() {
        fabAddToCart.setOnClickListener {
            viewModel.insertToCart(product)
            showSnackbarWithAction(it, "Added to Cart", "View", Snackbar.LENGTH_LONG) {
                goTo(CartActivity::class.java)
            }
        }
    }
}