package com.entexy.gardenguru.data.garden

import com.entexy.gardenguru.data.garden.cloud.GardenCloud
import com.entexy.gardenguru.data.garden.models.GardenData
import com.entexy.gardenguru.data.garden.models.GardenPlantData
import com.entexy.gardenguru.data.garden.models.Participant

class GardenMapper {
    fun map(cloud: GardenCloud): GardenData {
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