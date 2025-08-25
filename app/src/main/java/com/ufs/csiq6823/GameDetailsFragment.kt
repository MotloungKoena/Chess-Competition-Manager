package com.ufs.csiq6823

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment

class GameDetailsFragment : Fragment() {

    companion object {
        private const val ARG_WHITE = "ARG_WHITE"
        private const val ARG_BLACK = "ARG_BLACK"
        fun newInstance(white: String, black: String) = GameDetailsFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_WHITE, white)
                putString(ARG_BLACK, black)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_game_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val white = arguments?.getString(ARG_WHITE) ?: "White"
        val black = arguments?.getString(ARG_BLACK) ?: "Black"
        view.findViewById<TextView>(R.id.txtHeader).text = "$white (White) vs $black (Black)"
    }
}
