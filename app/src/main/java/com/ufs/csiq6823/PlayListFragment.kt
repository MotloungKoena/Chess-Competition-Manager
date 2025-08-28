package com.ufs.csiq6823

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class PlayListFragment : Fragment(R.layout.fragment_play_list) {

    private var weekTitle: String? = null
    private var weekDateLabel: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            weekTitle = it.getString(ARG_WEEK)
            weekDateLabel = it.getString(ARG_DATE_LABEL)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Header: "Week 6 — Sep 10, 2025" (falls back to just week if date missing)
        val header = buildString {
            append(weekTitle ?: getString(R.string.week_unknown))
            weekDateLabel?.takeIf { it.isNotBlank() }?.let { append(" — ").append(it) }
        }
        view.findViewById<TextView>(R.id.txtHeader).text = header

        // Sample list (replace with real data)
        val games = listOf(
            GameUI("Alice", "David", GameStatus.Pending),
            GameUI("Charlie", "Bob", GameStatus.WhiteWin),
            GameUI("Eve", "Fank", GameStatus.BlackWin),
            GameUI("Grace Hall", "Henry", GameStatus.Draw)
        )

        val rv = view.findViewById<RecyclerView>(R.id.rvGames)
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = GameAdapter(games) { game ->
            (activity as? MainActivity)?.openGameDetails(game.white, game.black)
        }

        view.findViewById<ExtendedFloatingActionButton>(R.id.fabAddGame)
            .setOnClickListener {
                (activity as? MainActivity)?.openAddGame(weekTitle ?: getString(R.string.week_unknown))
            }
    }

    companion object {
        private const val ARG_WEEK = "ARG_WEEK"
        private const val ARG_DATE_LABEL = "ARG_DATE_LABEL"

        fun newInstance(weekTitle: String, dateLabel: String?): PlayListFragment {
            return PlayListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_WEEK, weekTitle)
                    putString(ARG_DATE_LABEL, dateLabel)
                }
            }
        }
    }
}
