package com.franzandel.selleverything

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.franzandel.selleverything.data.BundleConstants
import com.franzandel.selleverything.extension.*
import com.franzandel.selleverything.newest.Product
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
            Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show()
        }
    }
}