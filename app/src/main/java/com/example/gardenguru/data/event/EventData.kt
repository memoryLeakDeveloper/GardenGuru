package com.example.gardenguru.data.event

import com.example.gardenguru.R
import java.util.*

data class EventData(val event: Event, val dateDMY: String, val isComplete: Boolean, val executor: String = "", val timeOfCompletionHHMM: String = ""){
    enum class Event(val nameRes: Int, val imageRes: Int, val inactiveImageRes: Int){
        Watering(R.string.watering, R.drawable.ic_watering, R.drawable.ic_watering_inactive),
        Pruning(R.string.pruning, R.drawable.ic_pruning, R.drawable.ic_pruning_inactive),
        Transfer(R.string.transfer, R.drawable.ic_transfer, R.drawable.ic_transfer_inactive),
        Spraying(R.string.spraying, R.drawable.ic_spraying,R.drawable.ic_spraying_inactive),
        TopDressing(R.string.top_dressing, R.drawable.ic_top_dressing, R.drawable.ic_top_dressing_inactive)
    }
}