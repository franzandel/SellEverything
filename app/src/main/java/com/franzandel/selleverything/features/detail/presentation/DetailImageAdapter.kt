package com.franzandel.selleverything.features.detail.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.R


/**
 * Created by Franz Andel on 21/08/20.
 * Android Engineer
 */

class DetailImageAdapter(private val context: Context, private val imageNames: List<String>) :
    RecyclerView.Adapter<DetailImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailImageViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_detail_image, parent, false)
        return DetailImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailImageViewHolder, position: Int) {
        val drawableName = imageNames[position]
        holder.bind(drawableName)
    }

    override fun getItemCount(): Int = imageNames.size
}