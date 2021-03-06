package com.franzandel.selleverything.features.detail.presentation

import android.text.SpannableStringBuilder
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.bold
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.franzandel.selleverything.R
import com.franzandel.selleverything.base.presentation.BaseActivity
import com.franzandel.selleverything.data.constants.BundleConstants
import com.franzandel.selleverything.data.constants.NumberConstants
import com.franzandel.selleverything.data.entity.Product
import com.franzandel.selleverything.extension.*
import com.franzandel.selleverything.features.cart.presentation.CartActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : BaseActivity() {

    private var tvCartItemCount: TextView? = null

    private val product by lazy {
        intent.extras?.getParcelable(BundleConstants.EXTRA_PRODUCT) ?: Product(
            id = "",
            cashback = "",
            discountPercentage = "",
            seller = "",
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

    override fun getLayoutId(): Int = R.layout.activity_detail

    override fun onActivityReady() {
        setSupportActionBar(mtbDetail)
        setupUI()
        setupUIClickListener()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        setupObserver()
        menuInflater.inflate(R.menu.menu_detail, menu)

        val cartMenuItem = menu?.findItem(R.id.menu_cart)
        val cartActionView = cartMenuItem?.actionView
        tvCartItemCount = cartActionView?.findViewById(R.id.cart_badge)

        cartActionView?.setOnClickListener {
            onOptionsItemSelected(cartMenuItem)
        }

        return true
    }

    private fun setupObserver() {
        viewModel.cartProducts.observe(this, Observer { products ->
            setupBadge(products)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_cart -> goTo(CartActivity::class.java)
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    private fun setupUI() {
        setupViewPager2()
        setupTabLayout()

        tvDetailDiscountedPrice.text =
            product.price.getDiscountedPrice(product.discountPercentage).toInt()
                .getFormattedIDNPrice()
        setupCashbackPercentage()
        setupDiscountPercentage()
        tvDetailOriginalPrice.text = product.price.toInt().getFormattedIDNPrice()
        tvDetailOriginalPrice.showStrikeThrough()
        tvDetailTitle.text = product.title
        setupDetailSeller()
        tvDetailWeight.text = product.weight.toInt().getFormattedWeight()
        tvDetailCondition.text = product.condition
        tvDetailMinOrder.text = product.minOrder.addUnit()
        tvDetailCategory.text = product.category
        tvDetailDescription.text = product.description
    }

    private fun setupCashbackPercentage() {
        if (product.cashback == NumberConstants.ZERO) {
            tvDetailCashbackPercentage.hide()
            setupDetailOriginalPrice(product.discountPercentage, tvDetailDiscountPercentage.id)
            tvDetailDiscountPercentage.setMargin(left = 40)
        } else {
            tvDetailCashbackPercentage.text = product.cashback.addCashbackPercentage()
        }
    }

    private fun setupDiscountPercentage() {
        if (product.discountPercentage == NumberConstants.ZERO) {
            tvDetailDiscountPercentage.hide()
            setupDetailOriginalPrice(product.cashback, tvDetailCashbackPercentage.id)
        } else {
            tvDetailDiscountPercentage.text = product.discountPercentage.addPercentage()
        }
    }

    private fun setupDetailOriginalPrice(cashbackOrDiscount: String, id: Int) {
        val originalPriceParams =
            tvDetailOriginalPrice.layoutParams as ConstraintLayout.LayoutParams
        if (cashbackOrDiscount == NumberConstants.ZERO) {
            tvDetailOriginalPrice.hide()
        } else {
            originalPriceParams.startToEnd = id
            originalPriceParams.topToTop = id
            originalPriceParams.bottomToBottom = id
        }
        tvDetailOriginalPrice.requestLayout()
    }

    private fun setupDetailSeller() {
        val ssDetailSeller = SpannableStringBuilder()
            .append("${getString(R.string.detail_seller)} ")
            .bold { append(product.seller) }

        tvDetailSeller.text = ssDetailSeller
    }

    private fun setupViewPager2() {
        val productImages = listOf(product.imageName)
        vpDetailProductImage.adapter = DetailImageAdapter(this, productImages)
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