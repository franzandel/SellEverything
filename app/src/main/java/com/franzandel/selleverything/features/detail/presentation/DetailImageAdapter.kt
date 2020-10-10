package com.franzandel.selleverything.features.detail.presentation

import android.content.Context
import android.view.View
import com.franzandel.selleverything.R
import com.franzandel.selleverything.base.BaseAdapter


/**
 * Created by Franz Andel on 21/08/20.
 * Android Engineer
 */

class DetailImageAdapter(context: Context, private val imageNames: List<String>) :
    BaseAdapter<DetailImageViewHolder, String>(context) {

    override fun getItemLayoutId(): Int = R.layout.item_detail_image

    override fun getViewHolder(view: View): DetailImageViewHolder = DetailImageViewHolder(view)

    override fun getCurrentList(): List<String> = imageNames

    override fun onBindViewHolder(holder: DetailImageViewHolder, position: Int) {
        val drawableName = imageNames[position]
        holder.bind(drawableName)
    }
}