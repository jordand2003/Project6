package com.example.bitfit_1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SleepEntryAdapter(
    private val context: Context,
    private var entries: List<SleepEntry>
) : RecyclerView.Adapter<SleepEntryAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        private val hoursTextView: TextView = itemView.findViewById(R.id.hoursTextView)

        fun bind(entry: SleepEntry) {
            dateTextView.text = entry.date
            hoursTextView.text = "${entry.hours} hours"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_sleep_entry, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(entries[position])
    }

    override fun getItemCount() = entries.size

    fun updateData(newEntries: List<SleepEntry>) {
        entries = newEntries
        notifyDataSetChanged()
    }
}