package com.example.gardenguru.ui.customview.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenguru.R
import com.example.gardenguru.databinding.CalendarLayoutBinding
import com.example.gardenguru.ui.customview.calendar.CalendarLayout.PeriodCallback

class CalendarLayout(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private lateinit var binding: CalendarLayoutBinding
    private lateinit var adapterDays: CalendarDaysAdapter
    private var adapterPeriod: CalendarPeriodAdapter
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
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        adapterDays = CalendarDaysAdapter()
        adapterPeriod = CalendarPeriodAdapter(periodCallback)
        binding = CalendarLayoutBinding.inflate(inflater, this)
        binding.recyclerDays.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterDays
            LinearSnapHelper().attachToRecyclerView(this)
        }
        binding.recyclerPeriod.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterPeriod
            addOnScrollListener(scrollCallback)
            layoutParams = layoutParams.apply { height = convertPxToDp(250F, context) }
            LinearSnapHelper().attachToRecyclerView(this)
        }
    }

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
