package com.example.gardenguru.data.garden

import com.example.gardenguru.core.Base
import com.example.gardenguru.data.plant.PlantData

data class GardenData(
    val name: String,
    val gardenOwner: String,
    val plants: ArrayList<PlantData>,
) : Base.DataObject