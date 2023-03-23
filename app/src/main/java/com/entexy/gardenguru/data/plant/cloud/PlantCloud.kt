package com.entexy.gardenguru.data.plant.cloud

import com.google.firebase.Timestamp

data class PlantCloud(
    var id: String,
    var name: String? = null,
    var localizedName: Map<String, String>? = null,
    var photo: String? = null,
    var careComplexity: String? = null,
    var description: String? = null,
    var localizeDescription: Map<String, String>? = null,
    var sunRelation: String? = null,
    var pestsIds: List<String>? = null,
    var reproduction: List<String>? = null,
    var benefitsIds: List<String>? = null,
    var pruning: String? = null,
    var plantingTime: Timestamp? = null,
    var watering: Int? = null,
    var spraying: Int? = null,
    var minTemp: Int? = null,
    var maxTemp: Int? = null,
)


