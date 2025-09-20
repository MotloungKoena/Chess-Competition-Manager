package com.ufs.csiq6823

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

data class GameUI(val id: String, val white: String, val black: String, val status: GameStatus)
enum class GameStatus { Pending, WhiteWin, BlackWin, Draw }

class GameAdapter(
    private val onClick: (GameUI) -> Unit
) : ListAdapter<GameUI, GameAdapter.VH>(DIFF) {

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        private val txtTitle: TextView = v.findViewById(R.id.txtTitle)
        private val txtStatus: TextView = v.findViewById(R.id.chipStatus)

        fun bind(row: GameUI) {
            txtTitle.text = "${row.white} vs ${row.black}"

            txtStatus.text = when (row.status) {
                GameStatus.Pending  -> itemView.context.getString(R.string.status_pending)
                GameStatus.WhiteWin -> itemView.context.getString(R.string.status_white_win)
                GameStatus.BlackWin -> itemView.context.getString(R.string.status_black_win)
                GameStatus.Draw     -> itemView.context.getString(R.string.status_draw)
            }

            itemView.setOnClickListener { onClick(row) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<GameUI>() {
            override fun areItemsTheSame(oldItem: GameUI, newItem: GameUI) =
                //oldItem.white == newItem.white && oldItem.black == newItem.black
                oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: GameUI, newItem: GameUI) = oldItem == newItem
        }
    }
}
