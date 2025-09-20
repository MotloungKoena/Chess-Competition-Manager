package com.ufs.csiq6823

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ufs.csiq6823.data.db.GameEntity
import kotlinx.coroutines.launch

class PlayListFragment : Fragment(R.layout.fragment_play_list) {

    private val TAG = "PlayListFragment"

    private var weekTitle: String? = null
    private var dateLabel: String? = null

    private val vm by activityViewModels<GamesViewModel> {
        GamesViewModel.factory((requireActivity().application as ChessApp).gameRepo)
    }

    private lateinit var adapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            weekTitle = it.getString("weekTitle") ?: getString(R.string.new_week)
            dateLabel = it.getString("dateLabel")
        }
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated , setting up RecyclerView")

        val rv = view.findViewById<RecyclerView>(R.id.rvGames)
        rv.layoutManager = LinearLayoutManager(requireContext())

        adapter = GameAdapter(

            onClick = { game ->
                val args = bundleOf(
                    "gameId" to game.id,
                    "white" to game.white,
                    "black" to game.black
                )
                findNavController().navigate(R.id.action_playList_to_gameDetails, args)
            }

        )
        rv.adapter = adapter

        val week = weekTitle ?: getString(R.string.new_week)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                vm.gamesForWeek(week).collect { entities ->
                    val ui = entities.map { e -> GameUI(e.id, e.white, e.black, e.status) }
                    adapter.submitList(ui)
                }

            }
        }

        view.findViewById<FloatingActionButton>(R.id.fabAddGame).setOnClickListener {
            val weekArg = weekTitle ?: getString(R.string.new_week)
            Log.i(TAG, "FAB Add Game tapped (week='$weekArg')")
            findNavController().navigate(
                R.id.action_playList_to_addGame,
                bundleOf("weekTitle" to weekArg)
            )
        }
    }

    override fun onResume() {
        super.onResume()
        val dateShort = dateLabel?.substringBeforeLast(",")?.trim()
        val titleText = when {
            weekTitle != null && dateShort != null -> "${weekTitle} \u2013 $dateShort"
            weekTitle != null && dateLabel != null -> "${weekTitle} \u2013 $dateLabel"
            else -> weekTitle ?: getString(R.string.app_name)
        }
        Log.d(TAG, "onResume , setting toolbar title: $titleText")

        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            title = titleText
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            Log.d(TAG, "Up/Home pressed , navigating back")
            findNavController().navigateUp()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun GameEntity.toUI(): GameUI =
        GameUI(
            id = id,
            white = white,
            black = black,
            status = GameStatus.Pending
        )

    companion object {
        private const val ARG_WEEK = "ARG_WEEK"
        private const val ARG_DATE = "ARG_DATE"

        fun newInstance(weekTitle: String, dateLabel: String?): PlayListFragment =
            PlayListFragment().apply {
                arguments = bundleOf(ARG_WEEK to weekTitle, ARG_DATE to dateLabel)
            }
    }
}
