package com.example.bitfit_1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomnavigation.BottomNavigationView

class AddEntryFragment : Fragment() {

    private val viewModel: SleepViewModel by activityViewModels {
        SleepViewModelFactory((requireActivity().application as BitFitApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_entry, container, false)

        // Find views
        val hoursEditText = view.findViewById<EditText>(R.id.hoursEditText)
        val dateEditText = view.findViewById<EditText>(R.id.dateEditText)
        val saveButton = view.findViewById<Button>(R.id.saveButton)

        // Handle save button click
        saveButton.setOnClickListener {
            val hours = hoursEditText.text.toString().toDoubleOrNull() ?: 0.0
            val date = dateEditText.text.toString()

            if (date.isNotEmpty()) {
                // Create a new SleepEntry
                val entry = SleepEntry(hours = hours, date = date)

                // Insert the entry into the database
                viewModel.insert(entry)

                // Clear the input fields
                hoursEditText.text.clear()
                dateEditText.text.clear()

                // Navigate to the Log fragment
                val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
                bottomNav.selectedItemId = R.id.nav_log
            } else {
                // Show an error if the date is empty
                dateEditText.error = "Date cannot be empty"
            }
        }

        return view
    }
}