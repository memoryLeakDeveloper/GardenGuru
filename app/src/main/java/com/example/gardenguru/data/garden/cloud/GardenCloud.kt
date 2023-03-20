package com.example.gardenguru.data.garden.cloud

import com.google.gson.annotations.SerializedName

data class GardenCloud(
    val id: String,
    val name: String,
    @SerializedName("summer_climate_type") val summerClimateType: String,
    val guru: String,
    val plants: ArrayList<GardenPlantCloud>,
    val participants: ArrayList<ParticipantsCloud>
)