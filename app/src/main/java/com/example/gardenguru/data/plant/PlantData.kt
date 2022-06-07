package com.example.gardenguru.data.plant

import com.example.gardenguru.core.Base
import com.example.gardenguru.data.benefit.BenefitData
import com.example.gardenguru.data.media.PhotoData
import com.example.gardenguru.data.pest.PestData
import com.example.gardenguru.data.reproduction.ReproductionData
import com.example.gardenguru.data.sun.relation.SunRelationData
import com.google.gson.annotations.SerializedName

data class PlantData(
    var id: String,
    @SerializedName("") var careComplexity: Int,
    var name: String,
    var description: String?,
    var photo: PhotoData,
    @SerializedName("") var sunRelation: SunRelationData?,
    var pests: ArrayList<PestData>?,
    var reproduction: ArrayList<ReproductionData>?,
    var benefits: ArrayList<BenefitData>?,
    var pruning: String?,
    var plantingTime: String?,
    @SerializedName("") var summerWatering: Int?,
    @SerializedName("") var summerSpraying: Int?,
    @SerializedName("") var summerFeeding: Int?,
    @SerializedName("") var summerMinTemp: Int?,
    @SerializedName("") var summerMaxTemp: Int?,
    @SerializedName("") var winterWatering: Int?,
    @SerializedName("") var winterSpraying: Int?,
    @SerializedName("") var winterFeeding: Int?,
    @SerializedName("") var winterMinTemp: Int?,
    @SerializedName("") var winterMaxTemp: Int?
) : Base.DataObject