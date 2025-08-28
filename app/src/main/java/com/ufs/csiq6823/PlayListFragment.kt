package com.ufs.csiq6823

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PlayListFragment : Fragment(R.layout.fragment_play_list) {

    private var weekTitle: String? = null
    private var dateLabel: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            weekTitle = it.getString(ARG_WEEK)
            dateLabel = it.getString(ARG_DATE)
        }
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = view.findViewById<RecyclerView>(R.id.rvGames)
        rv.layoutManager = LinearLayoutManager(requireContext())

        val games = listOf(
            GameUI("Alice", "David", GameStatus.Pending),
            GameUI("Charlie", "Bob", GameStatus.WhiteWin),
            GameUI("Eve", "Fank", GameStatus.BlackWin),
            GameUI("Grace Hall", "Henry", GameStatus.Draw)
        )

        rv.adapter = GameAdapter(games) {
            (activity as? MainActivity)?.openGameDetails("Alice", "David")
        }
        view.findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fabAddGame)
            .setOnClickListener {
                (activity as? MainActivity)?.openAddGame(weekTitle ?: getString(R.string.new_week))
            }
    }

    override fun onResume() {
        super.onResume()
        val dateShort = dateLabel?.substringBeforeLast(",")?.trim()

        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            title = when {
                weekTitle != null && dateShort != null -> "${weekTitle} \u2013 $dateShort"
                weekTitle != null && dateLabel != null -> "${weekTitle} \u2013 $dateLabel"
                else -> weekTitle ?: getString(R.string.app_name)
            }
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            activity?.onBackPressedDispatcher?.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val ARG_WEEK = "ARG_WEEK"
        private const val ARG_DATE = "ARG_DATE"

        fun newInstance(weekTitle: String, dateLabel: String?): PlayListFragment =
            PlayListFragment().apply {
                arguments = bundleOf(ARG_WEEK to weekTitle, ARG_DATE to dateLabel)
            }
    }
}
