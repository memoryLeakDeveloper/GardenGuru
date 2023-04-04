package com.entexy.gardenguru.ui.fragments.add_plant

import com.entexy.gardenguru.data.plant.PlantData

interface GetPlantInfo {
    fun getPlantInfo(): PlantData?
}