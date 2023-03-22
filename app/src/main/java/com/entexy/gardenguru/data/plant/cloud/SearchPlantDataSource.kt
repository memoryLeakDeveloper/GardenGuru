package com.entexy.gardenguru.data.plant.cloud

interface SearchPlantDataSource {

    suspend fun searchPlant(plantRecognitionString: String): PlantCloud

    class Base : SearchPlantDataSource {

        override suspend fun searchPlant(plantRecognitionString: String): PlantCloud {
            TODO("Not yet implemented")
        }
    }
}