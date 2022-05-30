package com.example.gardenguru.ui.customview.calendar

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenguru.data.enums.DaysMode
import com.example.gardenguru.databinding.CalendarDaysItemBinding

class CalendarDaysAdapter : RecyclerView.Adapter<CalendarDaysAdapter.ViewHolder>() {

    var currentDaysMode: DaysMode = DaysMode.Days

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CalendarDaysItemBinding.inflate(LayoutInflater.from(parent.context))
        binding.textView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, convertPxToDp(50F, parent.context))
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            if (isSkipItem(position)) textView.text = ""
            else {
                when (currentDaysMode) {
                    DaysMode.Days -> {
                        textView.text = (position - 1).toString()
                    }
                    DaysMode.Weeks -> {
                        textView.text = (position - 1).toString()
                    }
                    DaysMode.Months -> {
                        textView.text = (position - 1).toString()
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return when (currentDaysMode) {
            DaysMode.Days -> DAYS
            DaysMode.Weeks -> WEEKS
            DaysMode.Months -> MONTHS
        }
    }

    private fun isSkipItem(position: Int) = (position == 0 || position == 1 || position == itemCount - 2 || position == itemCount - 1)

    private fun convertPxToDp(px: Float, context: Context) = (px * context.resources.displayMetrics.density).toInt()

    companion object {
        const val DAYS = 34
        const val WEEKS = 14
        const val MONTHS = 10
    }

    class ViewHolder(val binding: CalendarDaysItemBinding) : RecyclerView.ViewHolder(binding.root)
}

