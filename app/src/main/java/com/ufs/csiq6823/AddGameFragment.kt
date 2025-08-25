package com.ufs.csiq6823

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment

class AddGameFragment : Fragment() {

    companion object {
        private const val ARG_WEEK = "ARG_WEEK"
        fun newInstance(weekTitle: String) = AddGameFragment().apply {
            arguments = Bundle().apply { putString(ARG_WEEK, weekTitle) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_add_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val white = view.findViewById<Spinner>(R.id.spinWhite)
        val black = view.findViewById<Spinner>(R.id.spinBlack)
        val players = listOf("Alice", "Bob", "Carol", "Dave")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, players)
        white.adapter = adapter
        black.adapter = adapter
    }
}
