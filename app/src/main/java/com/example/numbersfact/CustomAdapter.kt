package com.example.numbersfact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter (private val names: List<String>) :
    RecyclerView.Adapter<CustomAdapter.MyViewHolder>(){
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameTextView: TextView = itemView.findViewById(R.id.FactName)
        val ListTextView: TextView = itemView.findViewById(R.id.ListFacts)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rcview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.ListTextView.text = names[position]
        holder.nameTextView.text = "Fact №$position"
    }


    override fun getItemCount(): Int {
        return names.size
    }

}