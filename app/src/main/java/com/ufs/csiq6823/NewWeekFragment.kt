package com.ufs.csiq6823

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale

class NewWeekFragment : Fragment(R.layout.fragment_add_week) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actv = view.findViewById<AutoCompleteTextView>(R.id.actvStatus)
        val statusAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.week_statuses,
            android.R.layout.simple_list_item_1
        )
        actv.setAdapter(statusAdapter)

        actv.setText(getString(R.string.status_open), /* filter= */ false)

        val tilDate = view.findViewById<TextInputLayout>(R.id.tilDate)
        val edtDate = view.findViewById<TextInputEditText>(R.id.edtWeekDate)

        val openDatePicker = View.OnClickListener {
            val now = Calendar.getInstance()
            DatePickerDialog(
                requireContext(),
                { _, y, m, d ->
                    val fmt = SimpleDateFormat("MMM d, yyyy", Locale.US)
                    edtDate.setText(fmt.format(GregorianCalendar(y, m, d).time))
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        edtDate.setOnClickListener(openDatePicker)
        tilDate.setEndIconOnClickListener(openDatePicker)

        view.findViewById<Button>(R.id.btnCancel).setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        view.findViewById<Button>(R.id.btnSave).setOnClickListener {
            Toast.makeText(requireContext(), "New week added", Toast.LENGTH_SHORT).show()

            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    companion object {
        fun newInstance() = NewWeekFragment()
    }
}
