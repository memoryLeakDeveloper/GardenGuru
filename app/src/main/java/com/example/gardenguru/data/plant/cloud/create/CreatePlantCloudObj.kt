package com.example.gardenguru.data.plant.cloud.create

import com.example.gardenguru.core.Base
import com.example.gardenguru.data.benefit.BenefitData
import com.example.gardenguru.data.pest.PestData
import com.example.gardenguru.data.reproduction.ReproductionData
import com.example.gardenguru.data.sun.relation.SunRelationData
import com.google.gson.annotations.SerializedName

data class CreatePlantCloudObj(
    @SerializedName("name") val name: String? = null,
    @SerializedName("photos_ids") val photosIds: List<String>,
    @SerializedName("care_complexity") val careComplexity: Int? = null,
    @SerializedName("summer_watering") val summerWatering: Int? = null,
    @SerializedName("summer_spraying") val summerSpraying: Int? = null,
    @SerializedName("summer_feeding") val summerFeeding: Int? = null,
    @SerializedName("summer_min_temp") val summerMinTemp: Int? = null,
    @SerializedName("summer_max_temp") val summerMaxTemp: Int? = null,
    @SerializedName("winter_watering") val winterWatering: Int? = null,
    @SerializedName("winter_spraying") val winterSpraying: Int? = null,
    @SerializedName("winter_feeding") val winterFeeding: Int? = null,
    @SerializedName("winter_min_temp") val winterMinTemp: Int? = null,
    @SerializedName("winter_max_temp") val winterMaxTemp: Int? = null,
    @SerializedName("reproduction_ids") val reproductionIds: List<Int>? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("pests_ids") val pestsIds: List<String>? = null,
    @SerializedName("benefits_ids") val benefitsIds: List<Int>? = null,

) : Base.CloudObject
