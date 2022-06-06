package com.example.gardenguru.data.plant.cloud.create

import com.example.gardenguru.core.Base
import com.example.gardenguru.data.benefit.BenefitData
import com.example.gardenguru.data.pest.PestData
import com.example.gardenguru.data.reproduction.ReproductionData
import com.example.gardenguru.data.sun.relation.SunRelationData
import com.google.gson.annotations.SerializedName

data class CreatePlantBody(
    @SerializedName("name") val name: String,
    @SerializedName("photos_ids") val photosIds: ArrayList<String>,
    @SerializedName("care_complexity") val careComplexity: Int,
    @SerializedName("summer_watering") val summerWatering: Int,
    @SerializedName("summer_spraying") val summerSpraying: Int,
    @SerializedName("summer_feeding") val summerFeeding: Int,
    @SerializedName("summer_min_temp") val summerMinTemp: Int,
    @SerializedName("summer_max_temp") val summerMaxTemp: Int,
    @SerializedName("winter_watering") val winterWatering: Int,
    @SerializedName("winter_spraying") val winterSpraying: Int,
    @SerializedName("winter_feeding") val winterFeeding: Int,
    @SerializedName("winter_min_temp") val winterMinTemp: Int,
    @SerializedName("winter_max_temp") val winterMaxTemp: Int,
    @SerializedName("reproduction_ids") val reproductionIds: ArrayList<Int>,
    @SerializedName("description") val description: String,
    @SerializedName("pests_ids") val pestsIds: ArrayList<String>,
    @SerializedName("benefits_ids") val benefitsIds: ArrayList<Int>,

) : Base.CloudObject
