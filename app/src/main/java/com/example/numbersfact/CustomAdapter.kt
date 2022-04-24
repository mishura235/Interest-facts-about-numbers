package com.example.numbersfact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    private var facts = mutableListOf<String>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.FactName)
        val listTextView: TextView = itemView.findViewById(R.id.ListFacts)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rcview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.listTextView.text = facts[position]
        holder.nameTextView.text = "Fact №$position"
    }


    override fun getItemCount(): Int {
        return facts.size
    }

    fun addData(fact: String) {
        facts.add(fact)
        notifyDataSetChanged()
    }

    // TODO Это можно будет использовать для очистки RV - как пример.
    fun clearData(){
        facts.clear()
        notifyDataSetChanged()
    }
}