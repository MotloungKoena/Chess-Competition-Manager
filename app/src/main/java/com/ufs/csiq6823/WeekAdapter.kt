package com.ufs.csiq6823

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip

data class WeekUIi(
    val title: String,
    val subtitle: String,
    val isOpen: Boolean
)

class WeekAdapter(
    private var weeks: List<WeekUI>,
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
    fun submitList(newList: List<WeekUI>) {
        weeks = newList
        notifyDataSetChanged()
    }
}
