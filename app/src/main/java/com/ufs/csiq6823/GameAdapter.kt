package com.ufs.csiq6823

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class GameUI(val match: String, val result: String)

class GameAdapter(
    private val items: List<GameUI>,
    private val onOpen: (GameUI) -> Unit
) : RecyclerView.Adapter<GameAdapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMatch: TextView = itemView.findViewById(R.id.tvMatch)
        val tvResult: TextView = itemView.findViewById(R.id.tvResult)
        val btnOpen: Button = itemView.findViewById(R.id.btnOpenGame)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_game, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.tvMatch.text = item.match
        holder.tvResult.text = item.result
        holder.itemView.setOnClickListener { onOpen(item) }
        holder.btnOpen.setOnClickListener { onOpen(item) }
    }

    override fun getItemCount(): Int = items.size
}
