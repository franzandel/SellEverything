package com.franzandel.selleverything.cart

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.R
import com.franzandel.selleverything.data.constants.BundleConstants
import com.franzandel.selleverything.databinding.ItemCartBinding
import com.franzandel.selleverything.detail.DetailActivity
import com.franzandel.selleverything.extension.getDiscountedPrice
import com.franzandel.selleverything.extension.getFormattedIDNPrice
import com.franzandel.selleverything.extension.goTo
import com.franzandel.selleverything.newest.Product
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.item_cart.view.*

/**
 * Created by Franz Andel on 17/09/20.
 * Android Engineer
 */

class CartViewHolder(itemCartBinding: ItemCartBinding) :
    RecyclerView.ViewHolder(itemCartBinding.root) {

    private val _onCheckClicked = MutableLiveData<Unit>()
    val onCheckClicked: LiveData<Unit> = _onCheckClicked

    private val _onQtyMinusClicked = MutableLiveData<Product>()
    val onQtyMinusClicked: LiveData<Product> = _onQtyMinusClicked

    private val _onQtyPlusClicked = MutableLiveData<Product>()
    val onQtyPlusClicked: LiveData<Product> = _onQtyPlusClicked

    private val _onQtyChanged = MutableLiveData<Pair<Int, String>>()
    val onQtyChanged: LiveData<Pair<Int, String>> = _onQtyChanged

    private val _onDeleteClicked = MutableLiveData<Product>()
    val onDeleteClicked: LiveData<Product> = _onDeleteClicked

    private val activity = itemView.context as AppCompatActivity
    private val cartBinding = CartBinding()

    init {
        itemCartBinding.binding = cartBinding
        // forces the bindings to run immediately instead of delaying them until the next frame
        itemCartBinding.executePendingBindings()
        setupObserver()
    }

    private fun setupObserver() {
        cartBinding.onQtyChanged.observe(activity, Observer { qty ->
            _onQtyChanged.value = Pair(adapterPosition, qty)
        })
    }

    fun bind(product: Product) {
        itemView.apply {
            cbCheck.isChecked = product.isChecked
            tvCartContentSeller.text = product.seller
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
                MaterialAlertDialogBuilder(context)
                    .setTitle(context.getString(R.string.cart_delete_confirmation_title))
                    .setMessage(context.getString(R.string.cart_delete_confirmation_description))
                    .setNegativeButton(context.getString(R.string.delete_confirmation_negative_btn)) { _, _ -> }
                    .setPositiveButton(context.getString(R.string.delete_confirmation_positive_btn)) { _, _ ->
                        _onDeleteClicked.value = product
                    }
                    .show()
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