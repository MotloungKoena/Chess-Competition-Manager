package com.ufs.csiq6823

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SeasonListFragment : Fragment(R.layout.fragment_season_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = view.findViewById<RecyclerView>(R.id.rvWeeks)
        rv.layoutManager = LinearLayoutManager(requireContext())

        // Sample data matching the mock
        val weeks = listOf(
            WeekUI("Week 6", "Sep 10, 2025, 5:00 PM", true),
            WeekUI("Week 5", "Sep 3, 2025, 5:00 PM", false),
            WeekUI("Week 4", "Aug 27, 2025, 5:00 PM", false)
        )

        rv.adapter = WeekAdapter(weeks) { title, dateLabel ->
            (activity as? MainActivity)?.openPlayList(title, dateLabel)
        }

        // NEW WEEK: pass null for the date label (or rely on MainActivity overload)
        view.findViewById<View>(R.id.fabAddWeek).setOnClickListener {
            (activity as? MainActivity)?.openPlayList(getString(R.string.new_week), null)
            // if you added the overload, you can instead call:
            // (activity as? MainActivity)?.openPlayList(getString(R.string.new_week))
        }

        view.findViewById<View>(R.id.fabAddPlayer).setOnClickListener {
            (activity as? MainActivity)?.openAddPlayer()
        }
    }
}
