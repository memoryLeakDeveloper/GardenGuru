package com.example.gardenguru.ui.fragments.timetable

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenguru.R
import com.example.gardenguru.databinding.RvTimetableItemBinding
import java.util.*

class TimetableRecyclerAdapter(private val viewModel: TimetableViewModel) :
    RecyclerView.Adapter<TimetableRecyclerAdapter.EventsViewHolder>() {

    private val calendar = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_YEAR, -(3 + 7)) //the calendar is built a week before today's date, +3 days for empty cells
    }

    private val todayCalendar = Calendar.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        return EventsViewHolder(
            RvTimetableItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        with(holder.binding) {
            val positionCalendar = getCalendarToPosition(position)

            when (todayCalendar[Calendar.DAY_OF_YEAR] - positionCalendar[Calendar.DAY_OF_YEAR]) {
                0 -> tvDate.setText(R.string.today)
                -1 -> tvDate.setText(R.string.tomorrow)
                else -> {
                    val month = root.resources.getStringArray(R.array.calendar_month)[positionCalendar[Calendar.MONTH]]
                    tvDate.text = "${positionCalendar[Calendar.DAY_OF_MONTH]} $month"
                }
            }

            val item = viewModel.getEventForDay(positionCalendar)
            if (item != null) {
                if (item.events.find { !it.isComplete } == null) {
                    eventContainer.visibility = View.GONE
                    eventsCompleteContainer.visibility = View.VISIBLE
                    noEventContainer.visibility = View.GONE
                } else {
                    eventContainer.visibility = View.VISIBLE
                    eventsCompleteContainer.visibility = View.GONE
                    noEventContainer.visibility = View.GONE

                    tvPlantName.text = item.plant.name
                    tvPlantKind.text = item.plant.name
                    tvGardenName.text = "Сад $position"

                    rvEvents.layoutManager = LinearLayoutManager(root.context)
                    rvEvents.adapter = EventsRecyclerAdapter(item.events)
                }
            } else {
                eventContainer.visibility = View.GONE
                eventsCompleteContainer.visibility = View.GONE
                noEventContainer.visibility = View.VISIBLE
            }
        }
    }

    private fun getCalendarToPosition(position: Int): Calendar {
        return (calendar.clone() as Calendar).apply {
            add(Calendar.DAY_OF_YEAR, position)
        }
    }

    class EventsViewHolder(val binding: RvTimetableItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return CalendarRecyclerAdapter.ITEM_COUNT
    }
}
