package com.ufs.csiq6823

import PlayerDetailViewModel
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.ufs.csiq6823.R
import kotlinx.coroutines.launch

class PlayerDetailFragment : Fragment(R.layout.fragment_player_detail) {

    private val vm: PlayerDetailViewModel by viewModels {
        val repo = (requireActivity().application as ChessApp).playerRepo
        PlayerDetailViewModel.factory(repo)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            title = getString(R.string.title_add_player)
            setDisplayHomeAsUpEnabled(true)
        }

        val edtFullName  = view.findViewById<TextInputEditText>(R.id.edtFullName)
        val edtNickname  = view.findViewById<TextInputEditText>(R.id.edtNickname)
        val edtSchool    = view.findViewById<TextInputEditText>(R.id.edtSchool)
        val edtStartRank = view.findViewById<TextInputEditText>(R.id.edtStartRank)
        val btnSave      = view.findViewById<MaterialButton>(R.id.btnSave)
        val btnCancel    = view.findViewById<MaterialButton>(R.id.btnCancel)

        btnCancel.setOnClickListener { findNavController().navigateUp() }
        btnSave.setOnClickListener {
            val full  = edtFullName.text?.toString().orEmpty()
            val nick  = edtNickname.text?.toString().orEmpty()
            val school= edtSchool.text?.toString().orEmpty()
            val rank  = edtStartRank.text?.toString()?.toIntOrNull()
            val grade = null

            vm.save(full = full, nick = nick, grade = grade)

             requireActivity().onBackPressedDispatcher.onBackPressed()
        }

    }

    override fun onDestroyView() {
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        super.onDestroyView()
    }
}
