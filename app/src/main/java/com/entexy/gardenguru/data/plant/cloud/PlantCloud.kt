package com.entexy.gardenguru.data.plant.cloud

import com.google.firebase.Timestamp
import com.google.gson.annotations.SerializedName

data class PlantCloud(
    @SerializedName("name") val name: String,
    @SerializedName("localized_name") val localizedName: Map<String, String>? = null,
    @SerializedName("photo") val photo: String,
    @SerializedName("care_complexity") val careComplexity: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("description_localize") val descriptionWithLocalize: Map<String, String>? = null,
    @SerializedName("sun_relation") val sunRelation: String? = null,
    @SerializedName("pests") val pestsIds: List<String>? = null,
    @SerializedName("reproduction") val reproduction: List<String>? = null,
    @SerializedName("benefits") val benefitsIds: List<String>? = null,
    @SerializedName("pruning") val pruning: String? = null,
    @SerializedName("planting_time") val plantingTime: Timestamp? = null,
    @SerializedName("watering") val watering: Int? = null,
    @SerializedName("spraying") val spraying: Int? = null,
    @SerializedName("min_temp") val minTemp: Int? = null,
    @SerializedName("max_temp") val maxTemp: Int? = null,
)


