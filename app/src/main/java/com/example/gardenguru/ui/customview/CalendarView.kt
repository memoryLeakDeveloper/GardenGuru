package com.example.gardenguru.ui.customview

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class CalendarView: RecyclerView {

    private var calendarAdapter: BaseCalendarAdapter? = null
    private var itemSelectedCallback: CalendarItemSelectedCallback? = null

    constructor(context: Context): super(context)
    constructor(context: Context, attr: AttributeSet): super(context, attr)
    constructor(context: Context, attr: AttributeSet, defStyleAttr: Int): super(context, attr, defStyleAttr)

    fun setCalendarAdapter(calendarAdapter: BaseCalendarAdapter){
        this.calendarAdapter = calendarAdapter

        adapter = calendarAdapter as Adapter<*>
    }
    fun setSelectedCallback(itemSelectedCallback: CalendarItemSelectedCallback){
        this.itemSelectedCallback = itemSelectedCallback
    }

    init {
        this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(this)

        scrollToPosition(3 + 7 + 3)

        addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if(newState == SCROLL_STATE_IDLE) {
                    val firstVisiblePosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    val calendarCenterItem = calendarAdapter?.getCalendarToPosition(firstVisiblePosition + 4)

                    if (calendarCenterItem != null){
                        itemSelectedCallback?.call(calendarCenterItem, firstVisiblePosition)
                    }
                }
            }
        })
    }

    interface BaseCalendarAdapter {
        fun getCalendarToPosition(position: Int): Calendar
    }
    interface CalendarItemSelectedCallback{
        fun call(calendar: Calendar, position: Int)
    }
}