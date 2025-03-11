package com.example.bitfit_1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {
    private val viewModel: SleepViewModel by activityViewModels {
        SleepViewModelFactory((requireActivity().application as BitFitApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val totalEntriesText = view.findViewById<TextView>(R.id.tv_total_entries)
        val avgHoursText = view.findViewById<TextView>(R.id.tv_avg_hours)
        val maxHoursText = view.findViewById<TextView>(R.id.tv_max_hours)
        val minHoursText = view.findViewById<TextView>(R.id.tv_min_hours)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allSleepEntries.collect { entries ->
                val totalEntries = entries.size
                val avgHours = entries.map { it.hours }.average()
                val maxHours = entries.maxOfOrNull { it.hours } ?: 0.0
                val minHours = entries.minOfOrNull { it.hours } ?: 0.0

                totalEntriesText.text = "Total Entries: $totalEntries"
                avgHoursText.text = "Average Hours: %.1f".format(avgHours)
                maxHoursText.text = "Max Hours: %.1f".format(maxHours)
                minHoursText.text = "Min Hours: %.1f".format(minHours)
            }
        }
    }
}