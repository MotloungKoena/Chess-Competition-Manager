package com.ufs.csiq6823

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip

data class WeekUI(
    val title: String,     // e.g., "Week 6"
    val subtitle: String,  // e.g., "Sep 10, 2025, 5:00 PM"
    val isOpen: Boolean
)

class WeekAdapter(
    private val weeks: List<WeekUI>,
    // onClick now gives you (weekTitle, dateLabel)
    private val onClick: (String, String?) -> Unit
) : RecyclerView.Adapter<WeekAdapter.VH>() {

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        val txtTitle: TextView = v.findViewById(R.id.txtWeekTitle)
        val txtSubtitle: TextView = v.findViewById(R.id.txtWeekSubtitle)
        val chipStatus: Chip = v.findViewById(R.id.chipStatus)

        init {
            v.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    val w = weeks[pos]
                    // Take only the date part before the first comma (if any)
                    val dateLabel = w.subtitle.substringBefore(",", w.subtitle).trim()
                    onClick(w.title, dateLabel)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_week, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(h: VH, pos: Int) {
        val w = weeks[pos]
        h.txtTitle.text = w.title
        h.txtSubtitle.text = w.subtitle

        if (w.isOpen) {
            h.chipStatus.text = h.itemView.context.getString(R.string.status_open)
            h.chipStatus.setChipBackgroundColorResource(R.color.status_open_bg)
        } else {
            h.chipStatus.text = h.itemView.context.getString(R.string.status_closed)
            h.chipStatus.setChipBackgroundColorResource(R.color.status_closed_bg)
        }
    }

    override fun getItemCount() = weeks.size
}
