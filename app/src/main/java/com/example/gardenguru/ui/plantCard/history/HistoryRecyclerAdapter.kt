package com.example.gardenguru.ui.plantCard.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenguru.R
import com.example.gardenguru.databinding.RvItemHistoryBinding

class HistoryRecyclerAdapter(private val viewModel: PlantCardHistoryViewModel) :
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
            when (position) {
                0 -> {
                    ivLineTop.visibility = View.GONE
                }
                itemCount - 1 -> {
                    ivLineBottom.visibility = View.GONE
                }
                else -> {
                    ivLineBottom.visibility = View.VISIBLE
                }
            }

            val plantEvent = viewModel.plantEvents.value!!.events[position]

            tvEventName.setText(plantEvent.event.eventNameRes)
            ivEventImage.setImageResource(plantEvent.event.eventImageRes)
            tvEventTime.text = plantEvent.dateDMY + if (plantEvent.isComplete) "-${plantEvent.dateDMY}" else ""

            if (plantEvent.isComplete){
                ivDoneImage.setImageResource(R.drawable.ic_done)
            }else ivDoneImage.setImageResource(R.drawable.ic_not_done)

        }
    }


    override fun getItemCount(): Int {
        return viewModel.plantEvents.value?.events?.size ?: 0
    }

    class EventsViewHolder(val binding: RvItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)
}
