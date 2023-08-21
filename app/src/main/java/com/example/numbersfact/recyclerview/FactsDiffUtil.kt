package com.example.numbersfact.recyclerview

import androidx.recyclerview.widget.DiffUtil

class FactsDiffUtil(
    val oldList: MutableList<String>,
    val newList: MutableList<String>
    ) : DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when{
            oldList[oldItemPosition] != newList[newItemPosition] -> false
            else -> {true}
        }
    }

}