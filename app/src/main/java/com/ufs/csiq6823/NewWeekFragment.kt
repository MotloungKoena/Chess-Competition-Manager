package com.ufs.csiq6823

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale

class NewWeekFragment : Fragment(R.layout.fragment_add_week) {

    private val vm: WeeksViewModel by viewModels {
        val app = requireActivity().application as ChessApp
        WeeksViewModel.Factory(app.weekRepo)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actv = view.findViewById<AutoCompleteTextView>(R.id.actvStatus)
        val statusAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.week_statuses,
            android.R.layout.simple_list_item_1
        )
        actv.setAdapter(statusAdapter)
        actv.setText(getString(R.string.status_open), false)

        val tilDate = view.findViewById<TextInputLayout>(R.id.tilDate)
        val edtDate = view.findViewById<TextInputEditText>(R.id.edtWeekDate)
        val edtTitle = view.findViewById<TextInputEditText>(R.id.edtWeekTitle)

        var selectedMillis: Long? = null

        val openDatePicker = View.OnClickListener {
            val now = Calendar.getInstance()
            DatePickerDialog(
                requireContext(),
                { _, y, m, d ->
                    val cal = GregorianCalendar(y, m, d)
                    selectedMillis = cal.timeInMillis
                    val fmt = SimpleDateFormat("MMM d, yyyy", Locale.US)
                    edtDate.setText(fmt.format(cal.time))
                    tilDate.error = null
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        edtDate.setOnClickListener(openDatePicker)
        tilDate.setEndIconOnClickListener(openDatePicker)

        view.findViewById<Button>(R.id.btnCancel).setOnClickListener {
            findNavController().popBackStack()
        }

        view.findViewById<Button>(R.id.btnSave).setOnClickListener {
            val title = view.findViewById<TextInputEditText>(R.id.edtWeekTitle)
                ?.text?.toString()?.trim().orEmpty()
            val status = view.findViewById<AutoCompleteTextView>(R.id.actvStatus)
                ?.text?.toString()?.trim().orEmpty()
            val millis = selectedMillis

            when {
                title.isBlank() -> {
                    Toast.makeText(requireContext(), getString(R.string.msg_enter_title), Toast.LENGTH_SHORT).show()
                }
                millis == null -> {
                    Toast.makeText(requireContext(), getString(R.string.msg_pick_date), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    vm.addWeek(title, millis, status.ifBlank { getString(R.string.status_open) })
                    Toast.makeText(requireContext(), getString(R.string.msg_week_saved), Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        }
    }
}
