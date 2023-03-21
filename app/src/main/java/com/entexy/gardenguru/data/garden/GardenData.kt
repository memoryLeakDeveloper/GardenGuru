package com.entexy.gardenguru.data.garden

import com.entexy.gardenguru.data.plant.PlantData

data class GardenData(val name: String, val gardenOwner: String, val plants: ArrayList<PlantData>, )