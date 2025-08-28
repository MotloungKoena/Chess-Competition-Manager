package com.ufs.csiq6823

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

class AddGameFragment : Fragment(R.layout.fragment_add_game) {

    companion object {
        private const val ARG_WEEK = "arg_week"

        fun newInstance(weekTitle: String): AddGameFragment =
            AddGameFragment().apply {
                arguments = bundleOf(ARG_WEEK to weekTitle)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Dropdowns
        val actvWhite = view.findViewById<AutoCompleteTextView>(R.id.actvWhite)
        val actvBlack = view.findViewById<AutoCompleteTextView>(R.id.actvBlack)

        val players = resources.getStringArray(R.array.player_names)
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, players)
        actvWhite.setAdapter(adapter)
        actvBlack.setAdapter(adapter)

        // Buttons
        view.findViewById<Button>(R.id.btnCancel).setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        view.findViewById<Button>(R.id.btnSave).setOnClickListener {
            // no-op for now; just go back
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}
