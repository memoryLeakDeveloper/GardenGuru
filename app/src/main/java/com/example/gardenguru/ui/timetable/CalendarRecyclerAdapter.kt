package com.example.gardenguru.ui.timetable

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenguru.R
import com.example.gardenguru.databinding.RvCalendarItemBinding
import com.example.gardenguru.ui.customview.CalendarView
import java.util.*

class CalendarRecyclerAdapter(val viewModel: TimetableViewModel, private val itemWidth: Int) :
    RecyclerView.Adapter<CalendarRecyclerAdapter.CalendarViewHolder>(), CalendarView.BaseCalendarAdapter {

    companion object {
        const val ITEM_COUNT = 48
    }

    private val calendar = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_YEAR, -(3 + 7)) //the calendar is built a week before today's date, +3 days for empty cells
    }

    private val calendarToday = Calendar.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return CalendarViewHolder(
            RvCalendarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ).apply {
            binding.root.layoutParams = binding.root.layoutParams.apply {
                width = itemWidth
            }
        }
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        with(holder.binding) {
            if (position < 3 || position + 3 >= ITEM_COUNT) {
                dayNum.text = ""
                dayName.text = ""
                dayHighlight.setImageResource(R.color.transparent)
            } else {
                val today = getCalendarToPosition(position)
                dayName.text =
                    dayName.resources.getStringArray(R.array.day_of_week)[today.get(Calendar.DAY_OF_WEEK) - 1]
                dayNum.text = today.get(Calendar.DAY_OF_MONTH).toString()

                populateDay(today, dayHighlight)
            }
        }
    }

    override fun getCalendarToPosition(position: Int): Calendar{
        return (calendar.clone() as Calendar).apply {
            add(Calendar.DAY_OF_YEAR, position)
        }
    }

    private fun populateDay(calendar: Calendar, imageView: AppCompatImageView) {
        if (calendar[Calendar.DAY_OF_YEAR] == calendarToday[Calendar.DAY_OF_YEAR]){
            imageView.setImageResource(R.drawable.green_circle)
        }else{
            val event = viewModel.getEventForDay(calendar)
            if (event != null){
                if (calendar[Calendar.DAY_OF_YEAR] > calendarToday[Calendar.DAY_OF_YEAR]){
                    imageView.setImageResource(R.drawable.green_ring)
                }else{
                    imageView.setImageResource(R.drawable.white_10_circle_with_stroke)
                }
            }else imageView.setImageResource(R.color.transparent)
        }

    }

    override fun getItemCount(): Int {
        return ITEM_COUNT
    }

    class CalendarViewHolder(val binding: RvCalendarItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}
