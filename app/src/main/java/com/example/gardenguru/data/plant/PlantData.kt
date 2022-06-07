package com.example.gardenguru.data.plant

import com.example.gardenguru.core.Base
import com.example.gardenguru.data.benefit.BenefitData
import com.example.gardenguru.data.media.PhotoData
import com.example.gardenguru.data.pest.PestData
import com.example.gardenguru.data.reproduction.ReproductionData
import com.example.gardenguru.data.sun.relation.SunRelationData

data class PlantData(
    var id: String? = null,
    var careComplexity: Int? = null,
    var name: String,
//    var plantType: String? = null,
    var code: String? = null,
    var description: String? = null,
    var photo: PhotoData,
    var sunRelation: SunRelationData? = null,
    var pests: ArrayList<PestData>? = null,
    var reproduction: ArrayList<ReproductionData>? = null,
    var benefits: ArrayList<BenefitData>? = null,
    var pruning: String? = null,
    var plantingTime: String ? = null,
    var summerWatering: Int ? = null,
    var summerSpraying: Int? = null,
    var summerFeeding: Int? = null,
    var summerMinTemp: Int? = null,
    var summerMaxTemp: Int? = null,
    var winterWatering: Int? = null,
    var winterSpraying: Int? = null,
    var winterFeeding: Int? = null,
    var winterMinTemp: Int? = null,
    var winterMaxTemp: Int? = null
) : Base.DataObject