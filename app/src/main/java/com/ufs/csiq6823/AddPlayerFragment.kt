package com.ufs.csiq6823

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment

class AddPlayerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_add_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val grades = (8..12).map { "Grade $it" }
        val spin = view.findViewById<Spinner>(R.id.spinGrade)
        spin.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, grades)
    }
}
