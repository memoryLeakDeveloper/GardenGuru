package com.example.gardenguru.ui.customview.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenguru.data.enums.DaysMode
import com.example.gardenguru.databinding.CalendarLayoutBinding
import com.example.gardenguru.ui.customview.calendar.CalendarLayout.PeriodCallback

class CalendarLayout(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var binding = CalendarLayoutBinding.inflate(LayoutInflater.from(context), this)
    private lateinit var adapterDays: CalendarDaysAdapter
    private lateinit var adapterPeriod: CalendarPeriodAdapter
    private val periodCallback = PeriodCallback { mode ->
        binding.recyclerDays.adapter = adapterDays.apply { currentDaysMode = mode }
    }
    private val scrollCallback = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            getCurrentRecyclerPosition(binding.recyclerPeriod).let { position ->
                if (adapterDays.currentDaysMode == getModeFromPosition(position)) return
                binding.recyclerDays.adapter = adapterDays.apply { currentDaysMode = getModeFromPosition(position) }
            }
        }
    }

    fun interface PeriodCallback {
        fun update(daysMode: DaysMode)
    }

    init {
        initDaysRv()
        initPeriodRv()
    }

    fun disableScrolling() {
        binding.recyclerDays.isNestedScrollingEnabled = false
        binding.recyclerPeriod.isNestedScrollingEnabled = false
    }

    private fun initDaysRv() {
        adapterDays = CalendarDaysAdapter()
        binding.recyclerDays.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterDays
            LinearSnapHelper().attachToRecyclerView(this)
        }
    }

    private fun initPeriodRv() {
        adapterPeriod = CalendarPeriodAdapter(periodCallback)
        binding.recyclerPeriod.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterPeriod
            addOnScrollListener(scrollCallback)
            layoutParams = layoutParams.apply { height = convertPxToDp(250F, context) }
            LinearSnapHelper().attachToRecyclerView(this)
        }
    }

    fun getValue(): Pair<Int, DaysMode> = Pair(getDaysRecyclerValue(), adapterDays.currentDaysMode)

    private fun getDaysRecyclerValue() =
        (binding.recyclerDays.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition() - 1

    private fun getCurrentRecyclerPosition(recycler: RecyclerView) =
        (recycler.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition() + 2

    private fun getPositionFromMode(mode: DaysMode) = when (mode) {
        DaysMode.Days -> 0
        DaysMode.Weeks -> 1
        DaysMode.Months -> 2
    }

    private fun getModeFromPosition(position: Int): DaysMode = when (position) {
        2 -> DaysMode.Days
        3 -> DaysMode.Weeks
        4 -> DaysMode.Months
        else -> {
            DaysMode.Days
        }
    }

    private fun convertPxToDp(px: Float, context: Context) = (px * context.resources.displayMetrics.density).toInt()

}
