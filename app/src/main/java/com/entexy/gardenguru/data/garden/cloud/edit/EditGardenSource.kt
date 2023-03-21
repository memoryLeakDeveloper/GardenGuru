package com.entexy.gardenguru.data.garden.cloud.edit

import com.entexy.gardenguru.core.exception.ErrorResponseCodeException
import okhttp3.MultipartBody
import javax.inject.Inject

interface EditGardenSource {

    suspend fun edit(token: String,
                     id: String,
                     name: String,
                     summerClimateType: String): Boolean

    class Base @Inject constructor(private val service: EditGardenService) : EditGardenSource {
        override suspend fun edit(
            token: String,
            id: String,
            name: String,
            summerClimateType: String
        ): Boolean {
            val namePart = MultipartBody.Part.createFormData("name", name)
            val summerClimateTypePart = MultipartBody.Part.createFormData("summer_climate_type", summerClimateType)

            val response = service.fetch(token, id, namePart, summerClimateTypePart).execute()
            if (response.code() == 200)

                return true
            throw ErrorResponseCodeException(response.code(), 200)
        }
    }
}