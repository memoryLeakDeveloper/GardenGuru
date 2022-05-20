package com.example.gardenguru.ui.timetable

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenguru.R
import com.example.gardenguru.databinding.RvCalendarItemBinding
import com.example.gardenguru.ui.customview.CalendarView
import java.util.*

class CalendarRecyclerAdapter(val viewModel: TimetableViewModel, val itemWidth: Int) :
    RecyclerView.Adapter<CalendarRecyclerAdapter.DesireExplanationViewHolder>(), CalendarView.BaseCalendarAdapter {

    companion object {
        const val ITEM_COUNT = 48
    }

    private val calendar = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_YEAR, -(3 + 7)) //the calendar is built a week before today's date, +3 days for empty cells
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DesireExplanationViewHolder {
        return DesireExplanationViewHolder(
            RvCalendarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ).apply {
            binding.root.layoutParams = binding.root.layoutParams.apply {
                width = itemWidth
            }
        }
    }

    override fun onBindViewHolder(holder: DesireExplanationViewHolder, position: Int) {
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

                Log.d("qqqqq", "item num: ${dayNum.text}, adapter pos: ${holder.layoutPosition}")
            }
        }
    }

    override fun getCalendarToPosition(position: Int): Calendar{
        return (calendar.clone() as Calendar).apply {
            add(Calendar.DAY_OF_YEAR, position)
        }
    }

    private fun populateDay(calendar: Calendar, imageView: AppCompatImageView) {
        when {
            calendar[Calendar.DAY_OF_WEEK] % 3 == 0 -> {
                imageView.setImageResource(R.drawable.green_ring)

            }
            calendar[Calendar.DAY_OF_WEEK] % 3 == 1 -> {
                imageView.setImageResource(R.drawable.white_10_circle_with_stroke)
            }
        }
    }

    override fun getItemCount(): Int {
        return ITEM_COUNT
    }

    class DesireExplanationViewHolder(val binding: RvCalendarItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}
