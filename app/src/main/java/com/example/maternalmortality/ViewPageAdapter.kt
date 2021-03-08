package com.example.maternalmortality

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.*

class ViewPageAdapter (private var title: List<String>,private var details: List<String>,private var images: List<Int>) : RecyclerView.Adapter<ViewPageAdapter.Pager2ViewHolder>() {

    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.ititle)
        val itemDetails: TextView = itemView.findViewById(R.id.idetails)
        val itemImage: ImageView = itemView.findViewById(R.id.iimage)

        init {
            itemImage.setOnClickListener {v:View ->
                val position = adapterPosition
                Toast.makeText(itemView.context,"You clicked on item #${position+1}",Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewPageAdapter.Pager2ViewHolder {
        return Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.info1,parent,false))
    }

    override fun getItemCount(): Int {
        return title.size
    }

    override fun onBindViewHolder(holder: ViewPageAdapter.Pager2ViewHolder, position: Int) {
        holder.itemTitle.text = title[position]
        holder.itemDetails.text = details[position]
        holder.itemImage.setImageResource(images[position])
    }

}