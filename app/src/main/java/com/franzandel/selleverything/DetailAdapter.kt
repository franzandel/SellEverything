package com.franzandel.selleverything

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.franzandel.selleverything.extension.getDrawableIdFromName


/**
 * Created by Franz Andel on 21/08/20.
 * Android Engineer
 */

class DetailAdapter(private val context: Context, imageNames: List<String>) :
    RecyclerView.Adapter<DetailAdapter.MyViewHolder?>() {

    private var imageNames: List<String> = listOf()

    init {
        this.imageNames = imageNames
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_view_pager2, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val drawableName = imageNames[position]
        holder.bind(drawableName)
    }

    override fun getItemCount(): Int {
        return imageNames.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivDetailSellEverything: ImageView = itemView.findViewById(R.id.ivDetailSellEverything)

        fun bind(drawableName: String) {
            itemView.apply {
                val drawableId = context.getDrawableIdFromName(drawableName)
                ivDetailSellEverything.setImageResource(drawableId)
            }
        }
    }
}