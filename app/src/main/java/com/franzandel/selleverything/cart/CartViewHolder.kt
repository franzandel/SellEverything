package com.franzandel.selleverything.cart

import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.R
import com.franzandel.selleverything.data.BundleConstants
import com.franzandel.selleverything.detail.DetailActivity
import com.franzandel.selleverything.extension.getDiscountedPrice
import com.franzandel.selleverything.extension.getFormattedIDNPrice
import com.franzandel.selleverything.extension.goTo
import com.franzandel.selleverything.extension.showToast
import com.franzandel.selleverything.newest.Product
import kotlinx.android.synthetic.main.item_cart.view.*

/**
 * Created by Franz Andel on 17/09/20.
 * Android Engineer
 */

class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val _onCheckClicked = MutableLiveData<Unit>()
    val onCheckClicked: LiveData<Unit> = _onCheckClicked

    private val _onQtyMinusClicked = MutableLiveData<Product>()
    val onQtyMinusClicked: LiveData<Product> = _onQtyMinusClicked

    private val _onQtyPlusClicked = MutableLiveData<Product>()
    val onQtyPlusClicked: LiveData<Product> = _onQtyPlusClicked

    fun bind(product: Product) {
        itemView.apply {
            cbCheck.isChecked = product.isChecked
            tvCartContentShopName.text = "Current Shop Name"
            tvCartContentLocation.text = product.location
//            val drawableId = context.getDrawableIdFromName(product.imageName)
//            ivCartContentProduct.setImageResource(drawableId)
            tvCartContentTitle.text = product.title
            tvCartContentPrice.text = product.price
                .getDiscountedPrice(product.discountPercentage)
                .toInt()
                .getFormattedIDNPrice()

            etCartContentQty.setText(product.currentQty.toString())

            if (product.currentQty > 1) {
                fabCartContentMinusQty.isEnabled = true
                fabCartContentMinusQty.backgroundTintList =
                    AppCompatResources.getColorStateList(context, R.color.colorGreen70)
            }

            cbCheck.setOnCheckedChangeListener { _, isChecked ->
                product.isChecked = isChecked
                _onCheckClicked.value = Unit
            }

            tvCartContentTitle.setOnClickListener {
                context.goTo(DetailActivity::class.java) {
                    putExtra(BundleConstants.EXTRA_PRODUCT, product)
                }
            }

            ivCartContentDeleteProduct.setOnClickListener {
                // TODO: MAKE DIALOG CONFIRMATION
                context.showToast("Show Dialog Confirmation")
            }

            fabCartContentMinusQty.setOnClickListener {
                var currentQty = etCartContentQty.text.toString().toInt()
                currentQty -= 1
                product.currentQty = currentQty
                etCartContentQty.setText(currentQty.toString())

                if (etCartContentQty.text.toString() == "1") {
                    fabCartContentMinusQty.isEnabled = false
                    fabCartContentMinusQty.backgroundTintList =
                        AppCompatResources.getColorStateList(context, R.color.colorGray)
                }

                if (cbCheck.isChecked) _onQtyMinusClicked.value = product
            }

            fabCartContentPlusQty.setOnClickListener {
                fabCartContentMinusQty.isEnabled = true
                fabCartContentMinusQty.backgroundTintList =
                    AppCompatResources.getColorStateList(context, R.color.colorGreen70)

                var currentQty = etCartContentQty.text.toString().toInt()
                currentQty += 1
                product.currentQty = currentQty
                etCartContentQty.setText(currentQty.toString())

                if (cbCheck.isChecked) _onQtyPlusClicked.value = product
            }
        }
    }
}