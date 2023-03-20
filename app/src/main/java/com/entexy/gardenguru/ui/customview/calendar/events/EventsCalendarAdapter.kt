package com.entexy.gardenguru.ui.customview.calendar.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.entexy.gardenguru.R
import com.entexy.gardenguru.databinding.ItemEventsCalendarBinding
import java.util.*

class EventsCalendarAdapter(private val itemWidth: Int) : RecyclerView.Adapter<EventsCalendarAdapter.ViewHolder>() {

    private val calendarToday = Calendar.getInstance()
    private val calendar = Calendar.getInstance()
    private var selectedPosition: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemEventsCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)).apply {
            binding.root.layoutParams = binding.root.layoutParams.apply {
                width = itemWidth
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val today = getCalendarToPosition(position)
        with(holder.binding) {
            if (position == selectedPosition) {
                root.background = ContextCompat.getDrawable(root.context, R.drawable.spinner_background)
            } else {
                root.background = null
            }
            root.setOnClickListener {
                if (holder.bindingAdapterPosition == selectedPosition) return@setOnClickListener
                notifyItemChanged(selectedPosition)
                root.background = ContextCompat.getDrawable(root.context, R.drawable.spinner_background)
                selectedPosition = holder.bindingAdapterPosition
            }
            textDay.text = textDay.resources.getStringArray(R.array.day_of_week)[today.get(Calendar.DAY_OF_WEEK) - 1]
            textDate.text = today.get(Calendar.DAY_OF_MONTH).toString()
        }
    }

    private fun getCalendarToPosition(position: Int) = (calendar.clone() as Calendar).apply { add(Calendar.DAY_OF_YEAR, position) }

    override fun getItemCount() = 5

    class ViewHolder(val binding: ItemEventsCalendarBinding) : RecyclerView.ViewHolder(binding.root)
}