package com.entexy.gardenguru.ui.fragments.timetable

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.entexy.gardenguru.R
import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.databinding.RvEventItemBinding

class EventsRecyclerAdapter(
    private val events: ArrayList<EventData>,
    private val eventSelectedCallback: (eventData: EventData) -> Unit
) :
    RecyclerView.Adapter<EventsRecyclerAdapter.EventsViewHolder>() {

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

            cbEvent.setOnCheckedChangeListener(null)

            ivEventImage.setImageResource(item.event.imageRes)
            tvEventName.setText(item.event.nameRes)
            tvPlantVarietyAndName.text = root.resources.getString(R.string.plant_name_and_variety, item.plantName, item.plantVariety)

            cbEvent.isChecked = item.isComplete

            root.setOnClickListener {
                cbEvent.isChecked = !cbEvent.isChecked
            }

            cbEvent.setOnCheckedChangeListener { _, isChecked ->
                item.isComplete = isChecked
                eventSelectedCallback(item)
            }
        }
    }

    class EventsViewHolder(val binding: RvEventItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return events.size
    }
}
