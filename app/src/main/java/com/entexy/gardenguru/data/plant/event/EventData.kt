package com.entexy.gardenguru.data.plant.event

import android.os.Parcelable
import com.entexy.gardenguru.R
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class EventData(
    val id: String,
    val isCompleted: Boolean,
    val eventTime: Date,
    val eventType: EventType
) : Parcelable {
    enum class EventType(val cloudValue: String, val nameRes: Int, val imageRes: Int, val inactiveImageRes: Int) {
        Watering("WATERING", R.string.watering, R.drawable.ic_watering, R.drawable.ic_watering_inactive),
        Pruning("PRUNING", R.string.pruning, R.drawable.ic_pruning, R.drawable.ic_pruning_inactive),
        Transfer("TRANSFER", R.string.transfer, R.drawable.ic_transfer, R.drawable.ic_transfer_inactive),
        Spraying("SPRAYING", R.string.spraying, R.drawable.ic_spraying, R.drawable.ic_spraying_inactive),
        TopDressing("TOP_DRESSING", R.string.top_dressing, R.drawable.ic_top_dressing, R.drawable.ic_top_dressing_inactive),
        Create("CREATE", R.string.create_plant, R.drawable.ic_top_dressing, R.drawable.ic_top_dressing_inactive),
    }
}