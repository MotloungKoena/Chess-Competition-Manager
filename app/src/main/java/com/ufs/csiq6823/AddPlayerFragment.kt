package com.ufs.csiq6823

import PlayerDetailViewModel
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.ufs.csiq6823.data.PlayerRepository
import com.ufs.csiq6823.data.db.AppDatabase

class AddPlayerFragment : Fragment(R.layout.fragment_add_player) {

    private val TAG = "AddPlayerFragment"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(savedInstanceState=${savedInstanceState != null})")
    }
    private val vm: PlayerDetailViewModel by viewModels {
        val dao = AppDatabase.getInstance(requireContext()).playerDao()
        PlayerDetailViewModel.factory(PlayerRepository(dao))
    }
    private val vmm: PlayerDetailViewModel by viewModels {
        val repo = (requireActivity().application as ChessApp).playerRepo
        PlayerDetailViewModel.factory(repo)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated()")

        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            title = getString(R.string.title_add_player)
            setDisplayHomeAsUpEnabled(true)
        }

        val grades = resources.getStringArray(R.array.grades)
        Log.d(TAG, "Setting up grades dropdown with ${grades.size} items")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, grades)
        view.findViewById<AutoCompleteTextView>(R.id.actvGrade).setAdapter(adapter)

        view.findViewById<MaterialButton>(R.id.btnCancel).setOnClickListener {
            Log.i(TAG, "Cancel clicked -> navigateUp()")
            findNavController().navigateUp()
        }
        val fullEt = view.findViewById<TextInputEditText>(R.id.edtFullName)
        val nickEt = view.findViewById<TextInputEditText>(R.id.edtNickname)
        val gradeActv = view.findViewById<MaterialAutoCompleteTextView>(R.id.actvGrade)

        val btnSave   = view.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnSave)
        val btnCancel = view.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnCancel)

        gradeActv.setAdapter(adapter)
        requireNotNull(fullEt) { "edtFullName missing from fragment_add_player" }
        requireNotNull(nickEt) { "edtNickname missing from fragment_add_player" }
        requireNotNull(gradeActv) { "actvGrade missing from fragment_add_player" }
        btnCancel.setOnClickListener { findNavController().navigateUp() }

        btnSave.setOnClickListener {
            val full  = fullEt.text?.toString()?.trim().orEmpty()
            val nick  = nickEt.text?.toString()?.trim().orEmpty()
             val grade  = gradeActv.text?.toString()
                ?.replace("Grade", "", ignoreCase = true)
                ?.trim()?.toIntOrNull()

            vm.save(full = full, nick = nick,      grade = grade)

            Snackbar.make(requireView(), "Player saved", Snackbar.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }


    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume()")
        (activity as? AppCompatActivity)?.supportActionBar?.title =
            getString(R.string.title_add_player)
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView()")
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        super.onDestroyView()
    }
}
