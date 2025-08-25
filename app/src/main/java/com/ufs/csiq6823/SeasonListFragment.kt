package com.ufs.csiq6823

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment

class SeasonListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_season_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Demo clicks to navigate (UI-only)
        view.findViewById<Button>(R.id.btnWeek1)?.setOnClickListener {
            (activity as? MainActivity)?.openPlayList("Week 1")
        }
        view.findViewById<Button>(R.id.btnWeek2)?.setOnClickListener {
            (activity as? MainActivity)?.openPlayList("Week 2")
        }
        view.findViewById<Button>(R.id.fabAddPlayer)?.setOnClickListener {
            (activity as? MainActivity)?.openAddPlayer()
        }
        view.findViewById<Button>(R.id.fabAddWeek)?.setOnClickListener {
            (activity as? MainActivity)?.openPlayList("New Week")
        }
    }
}
