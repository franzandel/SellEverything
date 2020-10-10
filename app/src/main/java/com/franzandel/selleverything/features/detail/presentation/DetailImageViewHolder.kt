package com.franzandel.selleverything.features.detail.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.extension.getDrawableIdFromName
import kotlinx.android.synthetic.main.item_detail_image.view.*

/**
 * Created by Franz Andel on 10/10/20.
 * Android Engineer
 */

class DetailImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(drawableName: String) {
        itemView.apply {
            val drawableId = context.getDrawableIdFromName(drawableName)
            ivDetailSellEverything.setImageResource(drawableId)
        }
    }
}