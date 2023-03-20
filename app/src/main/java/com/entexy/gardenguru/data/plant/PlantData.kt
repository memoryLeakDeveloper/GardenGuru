package com.entexy.gardenguru.data.plant

import com.entexy.gardenguru.data.benefit.BenefitData
import com.entexy.gardenguru.data.media.PhotoData
import com.entexy.gardenguru.data.pest.PestData
import com.entexy.gardenguru.data.reproduction.ReproductionData
import com.entexy.gardenguru.data.sun.relation.SunRelationData

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
)