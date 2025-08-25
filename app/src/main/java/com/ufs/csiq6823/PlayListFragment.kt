package com.ufs.csiq6823

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class PlayListFragment : Fragment() {

    companion object {
        private const val ARG_WEEK = "ARG_WEEK"
        fun newInstance(weekTitle: String) = PlayListFragment().apply {
            arguments = Bundle().apply { putString(ARG_WEEK, weekTitle) }
        }
    }

    private val weekTitle: String by lazy { arguments?.getString(ARG_WEEK) ?: "Week ?" }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_play_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<TextView>(R.id.txtWeekTitle).text = weekTitle

        // Demo navigation
        view.findViewById<Button>(R.id.btnGame1)?.setOnClickListener {
            (activity as? MainActivity)?.openGameDetails("Alice", "Bob")
        }
        view.findViewById<Button>(R.id.fabAddGame)?.setOnClickListener {
            (activity as? MainActivity)?.openAddGame(weekTitle)
        }
    }
}
