package com.entexy.gardenguru.ui.customview.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.entexy.gardenguru.R
import com.entexy.gardenguru.data.enums.DaysMode
import com.entexy.gardenguru.databinding.CalendarPeriodItemBinding

class CalendarPeriodAdapter(private val callback: CalendarLayout.PeriodCallback) :
    RecyclerView.Adapter<CalendarPeriodAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CalendarPeriodItemBinding.inflate(LayoutInflater.from(parent.context))
        binding.textView.layoutParams = ViewGroup.LayoutParams(convertPxToDp(100F, parent.context), convertPxToDp(50F, parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            when (position) {
                2 -> {
                    textView.apply {
                        setText(R.string.days)
                        setOnClickListener {
                            callback.update(DaysMode.Days)
                        }
                    }
                }
                3 -> textView.apply {
                    setText(R.string.weeks)
                    setOnClickListener {
                        callback.update(DaysMode.Weeks)
                    }
                }
                4 -> textView.apply {
                    setText(R.string.month)
                    setOnClickListener {
                        callback.update(DaysMode.Months)
                    }
                }
                else -> {}
            }
        }
    }

    override fun getItemCount() = 7

    private fun convertPxToDp(px: Float, context: Context) = (px * context.resources.displayMetrics.density).toInt()

    class ViewHolder(val binding: CalendarPeriodItemBinding) : RecyclerView.ViewHolder(binding.root)

}