package com.entexy.gardenguru.ui.fragments.plant_card.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.entexy.gardenguru.R
import com.entexy.gardenguru.data.plant.event.EventData
import com.entexy.gardenguru.databinding.RvItemHistoryBinding
import com.entexy.gardenguru.utils.toDmyHmString
import com.entexy.gardenguru.utils.toDmyString

class HistoryRecyclerAdapter(private val events: ArrayList<EventData>) :
    RecyclerView.Adapter<HistoryRecyclerAdapter.EventsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        return EventsViewHolder(
            RvItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        with(holder.binding) {

            val plantEvent = events[position]

            tvEventName.setText(plantEvent.eventType.nameRes)
            tvEventTime.text = if (plantEvent.isCompleted) plantEvent.eventTime.toDmyHmString()
            else plantEvent.eventTime.toDmyString()

            if (plantEvent.isCompleted) {
                ivDoneImage.setImageResource(R.drawable.ic_done)
                ivEventImage.setImageResource(plantEvent.eventType.imageRes)
                tvEventName.setTextColor(root.resources.getColor(R.color.white, null))
            } else {
                ivDoneImage.setImageResource(R.drawable.ic_not_done)
                ivEventImage.setImageResource(plantEvent.eventType.inactiveImageRes)
                tvEventName.setTextColor(root.resources.getColor(R.color.gray, null))
            }
        }
    }


    override fun getItemCount(): Int {
        return events.size
    }

    class EventsViewHolder(val binding: RvItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)
}
