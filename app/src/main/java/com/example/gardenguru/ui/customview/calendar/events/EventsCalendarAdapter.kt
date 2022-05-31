package com.example.gardenguru.ui.customview.calendar.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenguru.R
import com.example.gardenguru.databinding.ItemEventsCalendarBinding
import java.util.*

class EventsCalendarAdapter() : RecyclerView.Adapter<EventsCalendarAdapter.ViewHolder>() {

    private val calendarToday = Calendar.getInstance()
    private val calendar = Calendar.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemEventsCalendarBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val today = getCalendarToPosition(position)
        with(holder.binding) {
            textDay.text = textDay.resources.getStringArray(R.array.day_of_week)[today.get(Calendar.DAY_OF_WEEK) - 2]
            textDate.text = today.get(Calendar.DAY_OF_MONTH).toString()
        }
    }

    private fun getCalendarToPosition(position: Int) = (calendar.clone() as Calendar).apply { add(Calendar.DAY_OF_YEAR, position) }

    override fun getItemCount() = 5

    class ViewHolder(val binding: ItemEventsCalendarBinding) : RecyclerView.ViewHolder(binding.root)
}