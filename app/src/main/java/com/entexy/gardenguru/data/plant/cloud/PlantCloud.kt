package com.entexy.gardenguru.data.plant.cloud

import com.google.firebase.Timestamp

data class PlantCloud(
    var id: String,
    var name: String,
    var variety: String,
    var localizedName: Map<String, String>? = null,
    var photo: String,
    var cover: String,
    var careComplexity: String,
    var description: String,
    var localizeDescription: Map<String, String>? = null,
    var sunRelation: String,
    var pestsIds: List<String>? = null,
    var reproduction: List<String>,
    var benefitsIds: List<String>? = null,
    var pruning: String,
    var plantingTime: Timestamp,
    var watering: Int,
    var spraying: Int,
    var minTemp: Int,
    var maxTemp: Int,
)