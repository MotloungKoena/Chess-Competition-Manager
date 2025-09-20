package com.ufs.csiq6823

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class PlayersFragment : Fragment(R.layout.fragment_players) {


    private val vm: PlayersViewModel by viewModels {
        val app = requireActivity().application as ChessApp
        PlayersViewModel.factory(app.playerRepo)
    }

    private val adapter = PlayerListAdapter { row ->

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv: RecyclerView = view.findViewById(R.id.rvPlayers)
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter
        rv.addItemDecoration(
            DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        )

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.rows.collect { adapter.submitList(it) }
            }
        }


        view.findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            Log.i(TAG, "FAB: Add Player tapped")
            findNavController().navigate(R.id.action_seasonList_to_addPlayer)
        }

    }
}
