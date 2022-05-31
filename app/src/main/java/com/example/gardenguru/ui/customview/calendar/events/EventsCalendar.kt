package com.example.gardenguru.ui.customview.calendar.events

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenguru.R
import com.example.gardenguru.databinding.CardEventsCalendarBinding
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat


class EventsCalendar(context: Context, attrs: AttributeSet) : LinearLayoutCompat(context, attrs) {

    private var binding = CardEventsCalendarBinding.inflate(LayoutInflater.from(context), this)

    init {
        orientation = VERTICAL
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
        binding.recycler.layoutManager = LinearLayoutManager(context).apply { orientation = RecyclerView.HORIZONTAL }
        binding.recycler.adapter = EventsCalendarAdapter()
    }
}