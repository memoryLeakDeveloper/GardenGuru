package com.example.gardenguru.data.garden

import com.example.gardenguru.core.Base
import com.example.gardenguru.data.benefit.BenefitData
import com.example.gardenguru.data.pest.PestData
import com.example.gardenguru.data.photo.PhotoData
import com.example.gardenguru.data.plant.PlantData
import com.example.gardenguru.data.reproduction.ReproductionData
import com.example.gardenguru.data.sun.relation.SunRelationData

data class GardenData(
    val name: String,
    val gardenOwner: String,
    val plants: ArrayList<PlantData>,
) : Base.DataObject