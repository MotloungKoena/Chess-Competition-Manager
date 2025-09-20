package com.ufs.csiq6823

import PlayerDetailViewModel
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.ufs.csiq6823.data.PlayerRepository
import com.ufs.csiq6823.data.db.AppDatabase
import kotlinx.coroutines.launch

class AddGameFragment : Fragment(R.layout.fragment_add_game) {

    companion object {
        private const val TAG = "AddGameFragment"
        private const val ARG_WEEK = "weekTitle"
    }

    private val playersVm by activityViewModels<PlayersViewModel> {
        PlayersViewModel.factory((requireActivity().application as ChessApp).playerRepo)
    }
    private val gamesVm by activityViewModels<GamesViewModel> {
        GamesViewModel.factory((requireActivity().application as ChessApp).gameRepo)
    }

    private val vm: PlayerDetailViewModel by viewModels {
        val dao = AppDatabase.getInstance(requireContext()).playerDao()
        PlayerDetailViewModel.factory(PlayerRepository(dao))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weekTitle = arguments?.getString(ARG_WEEK)

        val actvWhite = view.findViewById<AutoCompleteTextView>(R.id.actvWhite)
        val actvBlack = view.findViewById<AutoCompleteTextView>(R.id.actvBlack)

        val namesAdapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.item_dropdown,
            mutableListOf()
        )
        actvWhite.setAdapter(namesAdapter)
        actvBlack.setAdapter(namesAdapter)

        fun enableInstantDropdown(ac: AutoCompleteTextView) {
            ac.threshold = 0
            ac.setOnFocusChangeListener { v, has -> if (has) ac.showDropDown() }
            ac.setOnClickListener { ac.showDropDown() }
        }
        enableInstantDropdown(actvWhite)
        enableInstantDropdown(actvBlack)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                playersVm.rows.collect { rows ->
                    val names = rows.map { it.name }
                    Log.d(TAG, "Players from DB: ${names.size}")
                    namesAdapter.clear()
                    namesAdapter.addAll(names)
                    namesAdapter.notifyDataSetChanged()
                }
            }
        }

        view.findViewById<Button>(R.id.btnCancel).setOnClickListener {
            findNavController().navigateUp()
        }

        view.findViewById<Button>(R.id.btnSave).setOnClickListener {
            val white = actvWhite.text?.toString()?.trim().orEmpty()
            val black = actvBlack.text?.toString()?.trim().orEmpty()

            when {
                white.isBlank() || black.isBlank() -> {
                    Toast.makeText(requireContext(), getString(R.string.msg_pick_both_players), Toast.LENGTH_SHORT).show()
                }
                white.equals(black, ignoreCase = true) -> {
                    Toast.makeText(requireContext(), getString(R.string.msg_pick_two_different_players), Toast.LENGTH_SHORT).show()
                }
                else -> {

                    val week = weekTitle ?: getString(R.string.new_week)
                    gamesVm.addGame(week, white, black)
                    Toast.makeText(requireContext(), getString(R.string.msg_game_saved), Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
            }
        }
    }
}
