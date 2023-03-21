package com.entexy.gardenguru.ui.fragments.plant_card.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.entexy.gardenguru.R
import com.entexy.gardenguru.databinding.RvItemHistoryBinding

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

            tvEventName.setText(plantEvent.event.nameRes)
            tvEventTime.text = plantEvent.dateDMY + if (plantEvent.isComplete) "-${plantEvent.dateDMY}" else ""

            if (plantEvent.executor.isEmpty()) {
                tvExecutor.visibility = View.GONE
            } else {
                tvExecutor.visibility = View.VISIBLE
                tvExecutor.text = root.resources.getString(R.string.performed_email, plantEvent.executor)
            }

            if (plantEvent.isComplete) {
                ivDoneImage.setImageResource(R.drawable.ic_done)
                ivEventImage.setImageResource(plantEvent.event.imageRes)
                tvEventName.setTextColor(root.resources.getColor(R.color.white, null))
            } else {
                ivDoneImage.setImageResource(R.drawable.ic_not_done)
                ivEventImage.setImageResource(plantEvent.event.inactiveImageRes)
                tvEventName.setTextColor(root.resources.getColor(R.color.gray, null))
            }

        }
    }


    override fun getItemCount(): Int {
        return viewModel.plantEvents.value?.events?.size ?: 0
    }

    class EventsViewHolder(val binding: RvItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)
}
