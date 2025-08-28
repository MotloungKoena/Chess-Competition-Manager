package com.ufs.csiq6823

import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity

class GameDetailsFragment : Fragment(R.layout.fragment_game_details) {

    companion object {
        private const val ARG_WHITE = "arg_white"
        private const val ARG_BLACK = "arg_black"

        fun newInstance(white: String, black: String): GameDetailsFragment =
            GameDetailsFragment().apply {
                arguments = bundleOf(ARG_WHITE to white, ARG_BLACK to black)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val white = arguments?.getString(ARG_WHITE) ?: getString(R.string.sample_white_name)
        val black = arguments?.getString(ARG_BLACK) ?: getString(R.string.sample_black_name)

        view.findViewById<TextView>(R.id.txtWhiteName).text = white
        view.findViewById<TextView>(R.id.txtBlackName).text = black
        view.findViewById<ImageView>(R.id.imgWhite)
        view.findViewById<ImageView>(R.id.imgBlack)

        val results = resources.getStringArray(R.array.results)
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, results)
        view.findViewById<AutoCompleteTextView>(R.id.actvWhiteResult).setAdapter(adapter)
        view.findViewById<AutoCompleteTextView>(R.id.actvBlackResult).setAdapter(adapter)

        view.findViewById<Button>(R.id.btnSave).setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.title =
            getString(R.string.title_game_details)
    }
}
