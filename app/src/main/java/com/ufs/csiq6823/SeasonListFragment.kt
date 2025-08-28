package com.ufs.csiq6823

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SeasonListFragment : Fragment(R.layout.fragment_season_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = view.findViewById<RecyclerView>(R.id.rvWeeks)
        rv.layoutManager = LinearLayoutManager(requireContext())

        val weeks = listOf(
            WeekUI("Week 6", "Sep 10, 2025, 5:00 PM", true),
            WeekUI("Week 5", "Sep 3, 2025, 5:00 PM", false),
            WeekUI("Week 4", "Aug 27, 2025, 5:00 PM", false)
        )

        rv.adapter = WeekAdapter(weeks) { title, dateLabel ->
            (activity as? MainActivity)?.openPlayList(title, dateLabel)
        }

        view.findViewById<View>(R.id.fabAddWeek).setOnClickListener {
            (activity as? MainActivity)?.openNewWeek()
        }

        view.findViewById<View>(R.id.fabAddPlayer).setOnClickListener {
            (activity as? MainActivity)?.openAddPlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.title =
            getString(R.string.app_name)
    }
}
