package com.ufs.csiq6823

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView

data class GameUI(
    val white: String,
    val black: String,
    val status: GameStatus
)

enum class GameStatus { Pending, WhiteWin, BlackWin, Draw }

class GameAdapter(
    private val items: List<GameUI>,
    private val onClick: (GameUI) -> Unit
) : RecyclerView.Adapter<GameAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.txtTitle)
        val chip: TextView = view.findViewById(R.id.chipStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_game, parent, false)
        return VH(v)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val ctx = holder.itemView.context
        val item = items[position]

        holder.title.text = "${item.white} vs ${item.black}"

        val (label, bg, fg) = when (item.status) {
            GameStatus.Pending  -> Triple(ctx.getString(R.string.status_pending),
                color(ctx, R.color.chip_bg_pending), color(ctx, R.color.chip_fg_pending))
            GameStatus.WhiteWin -> Triple(ctx.getString(R.string.status_white_win),
                color(ctx, R.color.chip_bg_win), color(ctx, R.color.chip_fg_win))
            GameStatus.BlackWin -> Triple(ctx.getString(R.string.status_black_win),
                color(ctx, R.color.chip_bg_loss), color(ctx, R.color.chip_fg_loss))
            GameStatus.Draw     -> Triple(ctx.getString(R.string.status_draw),
                color(ctx, R.color.chip_bg_draw), color(ctx, R.color.chip_fg_draw))
        }

        holder.chip.text = label
        ViewCompat.setBackgroundTintList(holder.chip, ColorStateList.valueOf(bg))
        holder.chip.setTextColor(fg)

        holder.itemView.setOnClickListener { onClick(item) }
    }

    private fun color(ctx: Context, resId: Int) = ContextCompat.getColor(ctx, resId)
}
