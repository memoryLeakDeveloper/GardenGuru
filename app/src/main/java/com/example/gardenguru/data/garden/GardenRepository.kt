package com.example.gardenguru.data.garden

import android.accounts.NetworkErrorException
import com.example.gardenguru.data.auth.TokenHelper
import com.example.gardenguru.data.garden.cloud.GardenCloudDataSource
import com.example.gardenguru.data.garden.models.GardenData
import java.net.ConnectException
import javax.inject.Inject

interface GardenRepository {

    suspend fun getGardens(): ArrayList<GardenData>
    suspend fun deleteGarden()
    suspend fun createGarden()
    suspend fun addParticipant()
    suspend fun editGardenName()
    suspend fun editGardenSeason()


    class Base @Inject constructor(
        private val tokenHelper: TokenHelper.Base,
        private val gardenCloudDataSource: GardenCloudDataSource,
        private val gardenMapper: GardenMapper
    ) : GardenRepository {
        override suspend fun getGardens(): ArrayList<GardenData> {
            try {
                val clouds = gardenCloudDataSource.fetch(tokenHelper.getToken())

                val result = arrayListOf<GardenData>()
                clouds.forEach {
                    result.add(gardenMapper.map(it))
                }
                return result
            } catch (e: NetworkErrorException){
                e.printStackTrace()
            }catch (e: ConnectException){
                e.printStackTrace()
            }
            return arrayListOf()
        }

        override suspend fun deleteGarden() {
            TODO("Not yet implemented")
        }

        override suspend fun createGarden() {
            TODO("Not yet implemented")
        }

        override suspend fun addParticipant() {
            TODO("Not yet implemented")
        }

        override suspend fun editGardenName() {
            TODO("Not yet implemented")
        }

        override suspend fun editGardenSeason() {
            TODO("Not yet implemented")
        }

    }
}