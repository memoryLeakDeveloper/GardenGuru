package com.example.gardenguru.ui.timetable

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenguru.data.event.EventData
import com.example.gardenguru.databinding.RvEventItemBinding
import com.example.gardenguru.databinding.RvTimetableItemBinding
import java.util.*
import kotlin.collections.ArrayList

class EventsRecyclerAdapter(val events: ArrayList<EventData>) :
    RecyclerView.Adapter<EventsRecyclerAdapter.EventsViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        return EventsViewHolder(
            RvEventItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        with(holder.binding) {
            val item = events[position]

            ivEventImage.setImageResource(item.event.eventImageRes)
            tvEventName.setText(item.event.eventNameRes)
            cbEvent.isChecked = item.isComplete
        }
    }

    class EventsViewHolder(val binding: RvEventItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return events.size
    }
}
