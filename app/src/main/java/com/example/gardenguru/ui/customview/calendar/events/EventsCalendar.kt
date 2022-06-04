package com.example.gardenguru.ui.customview.calendar.events

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View.OnLayoutChangeListener
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenguru.R
import com.example.gardenguru.databinding.CardEventsCalendarBinding
import com.tbuonomo.viewpagerdotsindicator.setBackgroundCompat


class EventsCalendar(context: Context, attrs: AttributeSet) : LinearLayoutCompat(context, attrs) {

    private var binding = CardEventsCalendarBinding.inflate(LayoutInflater.from(context), this)
    private var adapter: EventsCalendarAdapter? = null
    private val layoutListener =
        OnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (adapter == null) {
                adapter = EventsCalendarAdapter(right / 6 - convertPxToDp(10F, context))
                binding.recycler.adapter = adapter
                removeListener()
            }
        }

    init {
        orientation = VERTICAL
        overScrollMode = OVER_SCROLL_NEVER
        setBackgroundCompat(ContextCompat.getDrawable(context, R.drawable.primary_card_background))
        binding.recycler.layoutManager = LinearLayoutManager(context).apply { orientation = RecyclerView.HORIZONTAL }
        binding.root.addOnLayoutChangeListener(layoutListener)
    }

    fun initView() {

    }

    private fun removeListener() {
        binding.root.removeOnLayoutChangeListener(layoutListener)
    }

    fun convertPxToDp(px: Float, context: Context) = (px * context.resources.displayMetrics.density).toInt()

}