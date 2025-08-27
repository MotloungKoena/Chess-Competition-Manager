package com.ufs.csiq6823

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PlayListFragment : Fragment(R.layout.fragment_play_list) {

    private var weekTitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weekTitle = arguments?.getString(ARG_WEEK)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Header
        view.findViewById<TextView>(R.id.txtWeekTitle).text =
            weekTitle ?: getString(R.string.week_unknown)
        activity?.title = weekTitle ?: getString(R.string.week_unknown)

        // List data (placeholder)
        val games = listOf(
            GameUI(getString(R.string.matchup_example), getString(R.string.result_pending))
        )

        val rv = view.findViewById<RecyclerView>(R.id.rvGames)
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = GameAdapter(games) { _ ->
            (activity as? MainActivity)?.openGameDetails("Alice", "Bob")
        }

        // Bottom "Add Game" button
        view.findViewById<Button>(R.id.fabAddGame).setOnClickListener {
            weekTitle?.let { (activity as? MainActivity)?.openAddGame(it) }
        }
    }

    companion object {
        private const val ARG_WEEK = "ARG_WEEK"
        fun newInstance(weekTitle: String) = PlayListFragment().apply {
            arguments = Bundle().apply { putString(ARG_WEEK, weekTitle) }
        }
    }
}
