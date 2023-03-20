package com.example.gardenguru.data.plant.cloud

import com.example.gardenguru.data.benefit.BenefitData
import com.example.gardenguru.data.pest.PestData
import com.example.gardenguru.data.reproduction.ReproductionData
import com.example.gardenguru.data.sun.relation.SunRelationData
import com.google.gson.annotations.SerializedName

data class PlantCloud(
    @SerializedName("id") val id: String? = null,
    @SerializedName("care_complexity") val careComplexity: Int? = null,
    @SerializedName("name") val name: String,
    @SerializedName("code") val code: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("photos") val photo: ArrayList<PhotoDataCloud>,
    @SerializedName("sunRelation") val sunRelation: SunRelationData? = null,
    @SerializedName("pests") val pests: ArrayList<PestData>? = null,
    @SerializedName("reproduction") val reproduction: ArrayList<ReproductionData>? = null,
    @SerializedName("benefits") val benefits: ArrayList<BenefitData>? = null,
    @SerializedName("pruning") val pruning: String? = null,
    @SerializedName("plantingTime") val plantingTime: String? = null,
    @SerializedName("summerWatering") val summerWatering: Int? = null,
    @SerializedName("summerSpraying") val summerSpraying: Int? = null,
    @SerializedName("summerFeeding") val summerFeeding: Int? = null,
    @SerializedName("summerMinTemp") val summerMinTemp: Int? = null,
    @SerializedName("summerMaxTemp") val summerMaxTemp: Int? = null,
    @SerializedName("winterWatering") val winterWatering: Int? = null,
    @SerializedName("winterSpraying") val winterSpraying: Int? = null,
    @SerializedName("winterFeeding") val winterFeeding: Int? = null,
    @SerializedName("winterMinTemp") val winterMinTemp: Int? = null,
    @SerializedName("winterMaxTemp") val winterMaxTemp: Int? = null
)
