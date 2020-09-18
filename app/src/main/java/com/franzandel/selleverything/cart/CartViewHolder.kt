package com.franzandel.selleverything.cart

import android.content.res.ColorStateList
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.R
import com.franzandel.selleverything.data.BundleConstants
import com.franzandel.selleverything.detail.DetailActivity
import com.franzandel.selleverything.extension.*
import com.franzandel.selleverything.newest.Product
import kotlinx.android.synthetic.main.item_cart.view.*

/**
 * Created by Franz Andel on 17/09/20.
 * Android Engineer
 */

class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(product: Product) {
        itemView.apply {
            tvCartContentShopName.text = "Current Shop Name"
            tvCartContentLocation.text = product.location
//            val drawableId = context.getDrawableIdFromName(product.imageName)
//            ivCartContentProduct.setImageResource(drawableId)
            tvCartContentTitle.text = product.title
            tvCartContentPrice.text = product.price
                .getDiscountedPrice(product.discountPercentage)
                .toInt()
                .getFormattedIDNPrice()

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
                // TODO: UPDATE TOTAL PRICE BALANCE
                var currentQty = etCartContentQty.text.toString().toInt()
                currentQty -= 1
                etCartContentQty.setText(currentQty.toString())

                if (etCartContentQty.text.toString() == "1") {
                    fabCartContentMinusQty.isEnabled = false
                    // This must be bug, Extended FAB is working with this code
                    fabCartContentMinusQty.backgroundTintList =
                        ColorStateList.valueOf(context.toColor(R.color.colorGray))
                }
            }

            fabCartContentPlusQty.setOnClickListener {
                // TODO: UPDATE TOTAL PRICE BALANCE
                fabCartContentMinusQty.isEnabled = true
                // This must be bug, Extended FAB is working with this code
                fabCartContentMinusQty.backgroundTintList =
                    ColorStateList.valueOf(context.toColor(R.color.colorGreen70))
                var currentQty = etCartContentQty.text.toString().toInt()
                currentQty += 1
                etCartContentQty.setText(currentQty.toString())
            }
        }
    }
}