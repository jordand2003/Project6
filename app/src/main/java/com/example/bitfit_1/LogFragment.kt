package com.example.bitfit_1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class LogFragment : Fragment() {

    private lateinit var adapter: SleepEntryAdapter
    private lateinit var recyclerView: RecyclerView
    private val viewModel: SleepViewModel by activityViewModels {
        SleepViewModelFactory((requireActivity().application as BitFitApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_log, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        adapter = SleepEntryAdapter(requireContext(), listOf())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allSleepEntries.collect { entries ->
                adapter.updateData(entries)
            }
        }
    }
}
