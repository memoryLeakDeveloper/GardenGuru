package com.entexy.gardenguru.ui.customview.calendar.events

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.entexy.gardenguru.R
import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.databinding.CardEventsCalendarBinding
import com.entexy.gardenguru.utils.convertPxToDp
import com.entexy.gardenguru.utils.isDaysEquals
import com.entexy.gardenguru.utils.toGone
import com.entexy.gardenguru.utils.toVisible
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat


class EventsCalendar(context: Context, attrs: AttributeSet) : LinearLayoutCompat(context, attrs) {

    private var binding = CardEventsCalendarBinding.inflate(LayoutInflater.from(context), this)
    private var adapter: EventsCalendarAdapter? = null

    init {
        orientation = VERTICAL
        overScrollMode = OVER_SCROLL_NEVER
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
        binding.recycler.itemAnimator = null
        binding.recycler.layoutManager = LinearLayoutManager(context).apply { orientation = RecyclerView.HORIZONTAL }
    }

    fun initView(events: List<EventData>) {
        adapter = EventsCalendarAdapter(context.convertPxToDp(50F), events) { calendar ->
            val currentEvents = events.filter { it.eventTime.isDaysEquals(calendar) }

            with(binding) {
                if (currentEvents.isEmpty()) {
                    textNoEvents.toVisible()
                    watering.toGone()
                    spraying.toGone()
                    feeding.toGone()
                } else {
                    textNoEvents.toGone()

                    if (currentEvents.find { it.eventType == EventData.EventType.Watering } != null) watering.toVisible()
                    else watering.toGone()

                    if (currentEvents.find { it.eventType == EventData.EventType.Feeding } != null) feeding.toVisible()
                    else feeding.toGone()

                    if (currentEvents.find { it.eventType == EventData.EventType.Spraying } != null) spraying.toVisible()
                    else spraying.toGone()
                }
            }
        }
        binding.recycler.adapter = adapter
    }
}