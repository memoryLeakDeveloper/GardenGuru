package com.example.gardenguru.data.garden

import com.example.gardenguru.core.Base
import com.example.gardenguru.data.garden.cloud.GardenCloud
import com.example.gardenguru.data.garden.models.GardenData
import com.example.gardenguru.data.garden.models.GardenPlantData
import com.example.gardenguru.data.garden.models.Participant

class GardenMapper: Base.CloudMapper<GardenCloud, GardenData> {
    override fun map(cloud: GardenCloud): GardenData {
        return GardenData(
            id = cloud.id,
            name = cloud.name,
            summerClimateSeason = GardenData.getSummerSeasonByValue(cloud.summerClimateType),
            plants = ArrayList(cloud.plants.map {
                GardenPlantData(it.id, it.name, it.plant, it.photos.first().file)
            }),
            participants = ArrayList(cloud.participants.map {
                Participant(it.id, it.user, Participant.getRoleByValue(it.role))
            })
        )
    }
}