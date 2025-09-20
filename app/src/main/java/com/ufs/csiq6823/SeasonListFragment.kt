package com.ufs.csiq6823

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class SeasonListFragment : Fragment(R.layout.fragment_season_list) {

    private lateinit var adapter: WeekAdapter
    private val vm: WeeksViewModel by viewModels {
        val app = requireActivity().application as ChessApp
        WeeksViewModel.Factory(app.weekRepo)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = view.findViewById<RecyclerView>(R.id.rvWeeks)
        rv.layoutManager = LinearLayoutManager(requireContext())

        val weekClick: (String, String?) -> Unit = { title, dateLabel ->
            findNavController().navigate(
                R.id.action_seasonList_to_playList,
                bundleOf("weekTitle" to title, "dateLabel" to dateLabel)
            )
        }

        adapter = WeekAdapter(emptyList(), weekClick)
        rv.adapter = adapter


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.weeks.collect { list ->
                    adapter.submitList(list)
                }
            }
        }

        view.findViewById<View>(R.id.fabAddWeek)?.setOnClickListener {
            findNavController().navigate(R.id.action_seasonList_to_newWeek)
        }

        view.findViewById<View>(R.id.fabAddPlayer)?.setOnClickListener {
            findNavController().navigate(R.id.action_seasonList_to_players)
        }
    }
}
