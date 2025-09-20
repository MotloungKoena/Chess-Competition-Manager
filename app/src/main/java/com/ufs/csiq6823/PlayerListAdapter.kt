package com.ufs.csiq6823

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class PlayerListAdapter(
    private val onClick: (PlayerRowUi) -> Unit
) : ListAdapter<PlayerRowUi, PlayerListAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<PlayerRowUi>() {
        override fun areItemsTheSame(o: PlayerRowUi, n: PlayerRowUi) = o.id == n.id
        override fun areContentsTheSame(o: PlayerRowUi, n: PlayerRowUi) = o == n
    }

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        private val name: TextView = v.findViewById(R.id.txtName)
        private val school: TextView = v.findViewById(R.id.txtSchool)
        private val rank: TextView = v.findViewById(R.id.txtRank)

        fun bind(row: PlayerRowUi) {
            val ctx = itemView.context

            name.text = row.name


            itemView.setOnClickListener { onClick(row) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_player, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind(getItem(position))
}
