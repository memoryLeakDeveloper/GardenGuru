package com.example.gardenguru.data.event

import com.example.gardenguru.data.plant.PlantData
import java.util.*
import kotlin.collections.ArrayList

data class PlantEventsData(val plant: PlantData, val events: ArrayList<EventData>)