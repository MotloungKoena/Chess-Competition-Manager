package com.ufs.csiq6823

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class AddPlayerFragment : Fragment(R.layout.fragment_add_player) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            title = getString(R.string.title_add_player)
            setDisplayHomeAsUpEnabled(true)
        }

        val grades = resources.getStringArray(R.array.grades)
        val adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_list_item_1, grades)
        view.findViewById<AutoCompleteTextView>(R.id.actvGrade).setAdapter(adapter)

        view.findViewById<MaterialButton>(R.id.btnCancel).setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        view.findViewById<MaterialButton>(R.id.btnSave).setOnClickListener {
            val full = view.findViewById<TextInputEditText>(R.id.edtFullName).text?.toString()?.trim().orEmpty()
            val nick = view.findViewById<TextInputEditText>(R.id.edtNickname).text?.toString()?.trim().orEmpty()
            val grade = view.findViewById<AutoCompleteTextView>(R.id.actvGrade).text?.toString()?.trim().orEmpty()

            Toast.makeText(requireContext(), "Player saved", Toast.LENGTH_SHORT).show()
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroyView() {
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.title =
            getString(R.string.title_add_player)
    }
}
