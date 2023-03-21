package com.entexy.gardenguru.data.event

import com.entexy.gardenguru.data.plant.PlantData

data class PlantEventsData(val plant: PlantData, val events: ArrayList<EventData>)