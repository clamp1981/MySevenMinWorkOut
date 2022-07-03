package com.example.mysevenminworkout

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mysevenminworkout.databinding.ItemHistoryRowBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.viewHolder>() {

    class viewHolder ( binding : ItemHistoryRowBinding ) : RecyclerView.ViewHolder( binding.root ){
        val llHistoryMain = binding.llHistoryItem
        val tvPosition = binding.tvPosition
        val tvItem = binding.tvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}