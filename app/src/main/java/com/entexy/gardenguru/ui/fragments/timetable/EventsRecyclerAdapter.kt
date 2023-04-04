package com.entexy.gardenguru.ui.fragments.timetable

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.App
import com.entexy.gardenguru.data.plant.event.PlantEventData
import com.entexy.gardenguru.databinding.RvEventItemBinding

class EventsRecyclerAdapter(
    private val events: ArrayList<PlantEventData>,
    private val eventsEditable: Boolean,
    private val eventSelectedCallback: (plantEventData: PlantEventData) -> Unit
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

            root.setOnClickListener(null)

            ivEventImage.setImageResource(item.event.imageRes)
            tvEventName.setText(item.event.nameRes)
            tvPlantVarietyAndName.text = root.resources.getString(
                R.string.plant_name_and_variety,
                item.plantCustomName,
                item.plantVariety[App.languagePreference.get()] ?: item.plantDefaultVariety
            )

            cbEvent.isChecked = item.isComplete

            if (eventsEditable) {
                ivEventImage.setImageResource(item.event.imageRes)
                tvEventName.setTextColor(root.resources.getColor(R.color.white, null))
                tvPlantVarietyAndName.setTextColor(root.resources.getColor(R.color.white, null))

                cbEvent.buttonDrawable = ResourcesCompat.getDrawable(root.resources, R.drawable.check_box_selector, root.context.theme)

                root.setOnClickListener {
//                    cbEvent.isChecked = !cbEvent.isChecked
                    eventSelectedCallback(item)
                }
            } else {
                cbEvent.buttonDrawable = ResourcesCompat.getDrawable(root.resources, R.drawable.check_box_selector_inactive, root.context.theme)

                ivEventImage.setImageResource(item.event.inactiveImageRes)
                tvEventName.setTextColor(root.resources.getColor(R.color.gray, null))
                tvPlantVarietyAndName.setTextColor(root.resources.getColor(R.color.gray, null))
            }
        }
    }

    class EventsViewHolder(val binding: RvEventItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return events.size
    }
}
