package com.example.gardenguru.data.plant

import com.example.gardenguru.core.Base
import com.example.gardenguru.data.benefit.BenefitData
import com.example.gardenguru.data.pest.PestData
import com.example.gardenguru.data.plant.cloud.PhotoDataCloud
import com.example.gardenguru.data.reproduction.ReproductionData
import com.example.gardenguru.data.sun.relation.SunRelationData

data class PlantData(
    var id: String,
    var care_complexity: Int,
    var name: String,
    var description: String,
    var photo: PhotoData,
    var sunRelation: SunRelationData,
    var pests: ArrayList<PestData>,
    var reproduction: ArrayList<ReproductionData>,
    var benefits: ArrayList<BenefitData>,
    var pruning: String,
    var plantingTime: String,
    var summerWatering: Int,
    var summerSpraying: Int,
    var summerFeeding: Int,
    var summerMinTemp: Int,
    var summerMaxTemp: Int,
    var winterWatering: Int,
    var winterSpraying: Int,
    var winterFeeding: Int,
    var winterMinTemp: Int,
    var winterMaxTemp: Int
) : Base.DataObject