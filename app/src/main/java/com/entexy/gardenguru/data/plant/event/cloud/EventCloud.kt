package com.entexy.gardenguru.data.plant.event.cloud

import com.google.firebase.Timestamp

data class EventCloud(
    var id: String? = null,
    var eventTime: Timestamp? = null,
    var eventType: String? = null,
    var plantId: String? = null
)