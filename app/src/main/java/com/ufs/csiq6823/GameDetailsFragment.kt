package com.ufs.csiq6823

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch


class GameDetailsFragment : Fragment(R.layout.fragment_game_details) {

    private val vm by activityViewModels<GamesViewModel> {
        GamesViewModel.factory((requireActivity().application as ChessApp).gameRepo)
    }

    private var gameId: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameId = requireArguments().getString("gameId") ?: ""

        val actvWhite = view.findViewById<AutoCompleteTextView>(R.id.actvWhiteResult)
        val actvBlack = view.findViewById<AutoCompleteTextView>(R.id.actvBlackResult)
        val resultAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.game_results,
            android.R.layout.simple_list_item_1
        )
        actvWhite.setAdapter(resultAdapter)
        actvBlack.setAdapter(resultAdapter)

        fun sync(sel: String) {
            if (actvWhite.text.toString() != sel) actvWhite.setText(sel, false)
            if (actvBlack.text.toString() != sel) actvBlack.setText(sel, false)
        }
        actvWhite.setOnItemClickListener { _, _, pos, _ -> sync(resultAdapter.getItem(pos) as String) }
        actvBlack.setOnItemClickListener { _, _, pos, _ -> sync(resultAdapter.getItem(pos) as String) }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.game(gameId).collect { g ->
                    g ?: return@collect
                    view.findViewById<TextView>(R.id.txtWhiteName)?.text = g.white
                    view.findViewById<TextView>(R.id.txtBlackName)?.text = g.black
                    val label = labelFor(g.status)
                    actvWhite.setText(label, false)
                    actvBlack.setText(label, false)
                }
            }
        }

        view.findViewById<Button>(R.id.btnSave).setOnClickListener {
            val sel = actvWhite.text?.toString()?.trim().orEmpty()
                .ifEmpty { actvBlack.text?.toString()?.trim().orEmpty() }
            val status = statusFromLabel(sel)
            if (status == GameStatus.Pending) {
                Toast.makeText(requireContext(), getString(R.string.msg_enter_title), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            vm.setResult(gameId, status)
            Toast.makeText(requireContext(), getString(R.string.msg_result_saved), Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

    }

    private fun labelFor(s: GameStatus) = when (s) {
        GameStatus.WhiteWin -> getString(R.string.result_white_win)
        GameStatus.BlackWin -> getString(R.string.result_black_win)
        GameStatus.Draw     -> getString(R.string.status_draw)
        GameStatus.Pending  -> ""
    }

    private fun statusFromLabel(label: String) = when {
        label.startsWith("White", true) -> GameStatus.WhiteWin
        label.startsWith("Black", true) -> GameStatus.BlackWin
        label.startsWith("Draw",  true) -> GameStatus.Draw
        else -> GameStatus.Pending
    }
}

