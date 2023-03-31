package com.entexy.gardenguru.ui.fragments.timetable

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout.LayoutParams
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.entexy.gardenguru.R
import com.entexy.gardenguru.data.plant.event.PlantEventData
import com.entexy.gardenguru.databinding.RvTimetableItemBinding
import com.entexy.gardenguru.utils.isDaysEquals
import com.entexy.gardenguru.utils.toGone
import com.entexy.gardenguru.utils.toVisible
import java.util.*

class TimetableRecyclerAdapter(private val eventCompletedCallback: (plantEventData: PlantEventData) -> Unit) :
    RecyclerView.Adapter<TimetableRecyclerAdapter.EventsViewHolder>() {

    private val calendar = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_YEAR, -(3 + 7))
    }

    private val todayCalendar = Calendar.getInstance()

    private var events = arrayListOf<PlantEventData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        return EventsViewHolder(
            RvTimetableItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        with(holder.binding) {
            val positionCalendar = getCalendarToPosition(position)

            val fromTodayToThisPosition = todayCalendar[Calendar.DAY_OF_YEAR] - positionCalendar[Calendar.DAY_OF_YEAR]
            when (fromTodayToThisPosition) {
                0 -> {
                    tvDate.setText(R.string.today)
                    tvDate.setTextColor(root.resources.getColor(R.color.white, null))
                }
                -1 -> {
                    tvDate.setTextColor(root.resources.getColor(R.color.gray, null))
                    tvDate.setText(R.string.tomorrow)
                }
                else -> {
                    tvDate.setTextColor(root.resources.getColor(R.color.gray, null))

                    val month = root.resources.getStringArray(R.array.calendar_month)[positionCalendar[Calendar.MONTH]]
                    tvDate.text = "${positionCalendar[Calendar.DAY_OF_MONTH]} $month"
                }
            }

            val items = getEventsForDay(positionCalendar)
            if (items.isNotEmpty()) {
                val hasIncompleteEvent = items.find { !it.isComplete } == null
                if (hasIncompleteEvent)
                    eventsCompleteContainer.toVisible()
                else
                    eventsCompleteContainer.toGone()

                noEventContainer.toGone()
                rvEvents.toVisible()

                rvEvents.layoutManager = LinearLayoutManager(root.context)
                rvEvents.adapter = EventsRecyclerAdapter(ArrayList(items), fromTodayToThisPosition == 0) { eventData ->
                    if ((items.find { !it.isComplete } == null) != hasIncompleteEvent)
                        notifyItemChanged(position)
                    eventCompletedCallback(eventData)
                }

            } else {
                eventsCompleteContainer.toGone()
                rvEvents.toGone()

                noEventContainer.toVisible()
            }

            val params = LinearLayoutCompat.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
            root.layoutParams = params.apply {
                setMargins(
                    0,
                    0,
                    0,
                    if (position == itemCount - 1) root.resources.getDimension(R.dimen.button_indent).toInt() else 0
                )
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setEvents(events: ArrayList<PlantEventData>) {
        this.events = events
        notifyDataSetChanged()
    }

    private fun getEventsForDay(calendar: Calendar): List<PlantEventData> {
        return events.filter {
            it.eventTime.isDaysEquals(calendar)
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
