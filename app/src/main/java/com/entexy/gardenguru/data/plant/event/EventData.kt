package com.entexy.gardenguru.data.plant.event

import android.os.Parcelable
import com.entexy.gardenguru.R
import com.entexy.gardenguru.data.plant.event.cloud.EventCloud
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class EventData(
    var id: String?,
    var isCompleted: Boolean,
    val eventTime: Calendar,
    val eventType: EventType,
    val plantId: String
) : Parcelable, Cloneable {
    enum class EventType(val cloudValue: String, val nameRes: Int, val imageRes: Int, val inactiveImageRes: Int) {
        Watering("WATERING", R.string.watering, R.drawable.ic_watering, R.drawable.ic_watering_inactive),
        Feeding("FEEDING", R.string.feeding, R.drawable.ic_feeding, R.drawable.ic_top_dressing_inactive),
        Transfer("TRANSFER", R.string.transfer, R.drawable.ic_transfer, R.drawable.ic_transfer_inactive),
        Spraying("SPRAYING", R.string.spraying, R.drawable.ic_spraying, R.drawable.ic_spraying_inactive),
        Create("CREATE", R.string.plant_added, R.drawable.plant_placeholder, R.drawable.ic_top_dressing_inactive),
    }

    override fun toString(): String =
        "[id: $id; isComplete: $isCompleted, eventTime: ${eventTime.time}, eventType: ${eventType.cloudValue}, plantId: $plantId]"

    public override fun clone(): EventData {
        return super.clone() as EventData
    }
}

fun EventData.mapToCloud(): EventCloud {
    return EventCloud(
        id,
        Timestamp(eventTime.time),
        eventType.cloudValue,
        plantId
    )
}

fun EventCloud.mapToData(): EventData? {
    return EventData(
        id ?: return null,
        true,
        Calendar.getInstance().apply { time = eventTime!!.toDate() },
        EventData.EventType.values().find {
            it.cloudValue == eventType
        } ?: EventData.EventType.Watering,
        plantId ?: return null
    )
}