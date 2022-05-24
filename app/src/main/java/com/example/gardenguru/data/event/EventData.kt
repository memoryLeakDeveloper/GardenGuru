package com.example.gardenguru.data.event

import com.example.gardenguru.R
import java.util.*

data class EventData(val event: Event, val isComplete: Boolean){
    enum class Event(val eventNameRes: Int, val eventImageRes: Int){
        Watering(R.string.watering, R.drawable.ic_watering),
        Transfer(R.string.transfer, R.drawable.ic_transfer),
        Spraying(R.string.spraying, R.drawable.ic_spraying),
        TopDressing(R.string.top_dressing, R.drawable.ic_top_dressing)
    }
}