package com.entexy.gardenguru.ui.fragments.add_plant

import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.data.plant.search.PlantSearchData

interface GetPlantInfo {
    fun getPlantInfo(): PlantSearchData?
}