package com.entexy.gardenguru.ui.fragments.timetable

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.entexy.gardenguru.R
import com.entexy.gardenguru.databinding.RvTimetableItemBinding
import java.util.*

class TimetableRecyclerAdapter(private val viewModel: TimetableViewModel) :
    RecyclerView.Adapter<TimetableRecyclerAdapter.EventsViewHolder>() {

    private val calendar = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_YEAR, -(3 + 7))
    }

    private val todayCalendar = Calendar.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        return EventsViewHolder(
            RvTimetableItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
                    eventsCompleteContainer.visibility = View.VISIBLE
                    noEventContainer.visibility = View.GONE
                } else {
                    eventsCompleteContainer.visibility = View.GONE
                    noEventContainer.visibility = View.GONE

                    rvEvents.layoutManager = LinearLayoutManager(root.context)
                    rvEvents.adapter = EventsRecyclerAdapter(item.events)
                }
            } else {
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
        return 48
    }
}
