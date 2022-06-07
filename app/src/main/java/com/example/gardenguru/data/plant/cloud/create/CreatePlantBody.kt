package com.example.gardenguru.data.plant.cloud.create

import com.example.gardenguru.core.Base
import com.example.gardenguru.data.benefit.BenefitData
import com.example.gardenguru.data.pest.PestData
import com.example.gardenguru.data.reproduction.ReproductionData
import com.example.gardenguru.data.sun.relation.SunRelationData
import com.google.gson.annotations.SerializedName

data class CreatePlantBody(
    val name: String,
    val garden: String,
    val plant: CreatePlantCloudObj,
) : Base.CloudObject
