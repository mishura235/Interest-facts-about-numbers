package com.example.numbersfact.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.numbersfact.R

class FactListAdapter : RecyclerView.Adapter<FactListAdapter.MyViewHolder>() {

    private var facts = mutableListOf<String>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.FactName)
        val listTextView: TextView = itemView.findViewById(R.id.ListFacts)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fact_recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.listTextView.text = facts[position]
        holder.nameTextView.text = "Fact №${position+1}"
    }


    override fun getItemCount(): Int {
        return facts.size
    }

    fun setFacts(facts: MutableList<String>) {
        val diffUtil = FactsDiffUtil(this.facts,facts)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        this.facts = facts
        diffResult.dispatchUpdatesTo(this)
    }


    fun clearData(){
        facts.clear()
        notifyDataSetChanged()
    }
}