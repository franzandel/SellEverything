package com.franzandel.selleverything.features.home.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.data.constants.BundleConstants
import com.franzandel.selleverything.data.entity.Product
import com.franzandel.selleverything.extension.*
import com.franzandel.selleverything.features.detail.presentation.DetailActivity
import kotlinx.android.synthetic.main.item_sell_everything.view.*


/**
 * Created by Franz Andel on 17/08/20.
 * Android Engineer
 */

class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(product: Product) {
        itemView.apply {
            fun setupSellEverythingImage() {
                val drawableId = context.getDrawableIdFromName(product.imageName)
                ivSellEverything.setImageResource(drawableId)
            }

            fun setupQtyLeft() {
                if (product.quantity.toInt() > 5) {
                    tvQtyLeft.hide()
                } else {
                    tvQtyLeft.text = product.quantity.addQtyLeft()
                }
            }

            fun setupDiscount() {
                if (product.discountPercentage == "0") {
                    tvDiscountPercentage.hide()
                    tvOriginalPrice.hide()
                } else {
                    tvDiscountPercentage.text = product.discountPercentage.addPercentage()
                    tvOriginalPrice.text = product.price.toInt().getFormattedIDNPrice()
                    tvOriginalPrice.showStrikeThrough()
                }
            }

            fun setupPrice() {
                if (product.discountPercentage == "0") {
                    tvDiscountedPrice.text = product.price.toInt().getFormattedIDNPrice()
                } else {
                    tvDiscountedPrice.text =
                        product.price.getDiscountedPrice(product.discountPercentage).toInt()
                            .getFormattedIDNPrice()
                }
            }

            setOnClickListener {
                context.goTo(DetailActivity::class.java) {
                    putExtra(BundleConstants.EXTRA_PRODUCT, product)
                }
            }

            setupSellEverythingImage()
            setupQtyLeft()
            tvTitle.text = product.title
            setupDiscount()
            setupPrice()
            tvSellerLocation.text = product.location
        }
    }
}