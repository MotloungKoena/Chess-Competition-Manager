package com.ufs.csiq6823

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class GameDetailsFragment : Fragment(R.layout.fragment_game_details) {

    private var whiteName: String? = null
    private var blackName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        whiteName = arguments?.getString(ARG_WHITE)
        blackName = arguments?.getString(ARG_BLACK)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Screen title
        activity?.title = getString(R.string.title_game_details)

        // Views
        val txtWhite = view.findViewById<TextView>(R.id.txtWhiteName)
        val txtBlack = view.findViewById<TextView>(R.id.txtBlackName)
        val ddWhite = view.findViewById<AutoCompleteTextView>(R.id.actvWhiteResult)
        val ddBlack = view.findViewById<AutoCompleteTextView>(R.id.actvBlackResult)
        val edtNotes = view.findViewById<EditText>(R.id.edtNotes)
        val btnSave = view.findViewById<Button>(R.id.btnSave)

        // Names (fallback samples if args missing)
        txtWhite.text = whiteName ?: getString(R.string.sample_white_name)
        txtBlack.text = blackName ?: getString(R.string.sample_black_name)

        // Dropdown data & adapters
        val results = resources.getStringArray(R.array.results)
        val dropAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, results)

        ddWhite.setAdapter(dropAdapter)
        ddBlack.setAdapter(dropAdapter)

        // Defaults to match the mock: White Win, Black Win
        if (results.isNotEmpty()) {
            ddWhite.setText(results.getOrNull(0) ?: "", false)
            ddBlack.setText(results.getOrNull(1) ?: "", false)
        }

        // Make taps open the dropdown
        ddWhite.setOnClickListener { ddWhite.showDropDown() }
        ddBlack.setOnClickListener { ddBlack.showDropDown() }

        // Save action (demo)
        btnSave.setOnClickListener {
            val whiteResult = ddWhite.text?.toString().orEmpty()
            val blackResult = ddBlack.text?.toString().orEmpty()
            val notes = edtNotes.text?.toString().orEmpty()

            val msg = "Saved:\nWhite=$whiteResult\nBlack=$blackResult\nNotes=${notes.ifBlank { "—" }}"
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()

            // navigate back
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

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
}
