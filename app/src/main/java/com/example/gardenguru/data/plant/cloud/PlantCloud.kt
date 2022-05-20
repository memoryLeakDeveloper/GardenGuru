package com.example.gardenguru.data.plant.cloud

import com.example.gardenguru.core.Base
import com.example.gardenguru.data.benefit.BenefitData
import com.example.gardenguru.data.pest.PestData
import com.example.gardenguru.data.photo.PhotoData
import com.example.gardenguru.data.reproduction.ReproductionData
import com.example.gardenguru.data.sun.relation.SunRelationData
import com.google.gson.annotations.SerializedName

data class PlantCloud(
    @SerializedName("id") val id: String,
    @SerializedName("care_complexity") val care_complexity: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("photo") val photo: ArrayList<PhotoData>,
    @SerializedName("sunRelation") val sunRelation: SunRelationData,
    @SerializedName("pests") val pests: ArrayList<PestData>,
    @SerializedName("reproduction") val reproduction: ArrayList<ReproductionData>,
    @SerializedName("benefits") val benefits: ArrayList<BenefitData>,
    @SerializedName("pruning") val pruning: String,
    @SerializedName("plantingTime") val plantingTime: String,
    @SerializedName("summerWatering") val summerWatering: Int,
    @SerializedName("summerSpraying") val summerSpraying: Int,
    @SerializedName("summerFeeding") val summerFeeding: Int,
    @SerializedName("summerMinTemp") val summerMinTemp: Int,
    @SerializedName("summerMaxTemp") val summerMaxTemp: Int,
    @SerializedName("winterWatering") val winterWatering: Int,
    @SerializedName("winterSpraying") val winterSpraying: Int,
    @SerializedName("winterFeeding") val winterFeeding: Int,
    @SerializedName("winterMinTemp") val winterMinTemp: Int,
    @SerializedName("winterMaxTemp") val winterMaxTemp: Int
) : Base.CloudObject
