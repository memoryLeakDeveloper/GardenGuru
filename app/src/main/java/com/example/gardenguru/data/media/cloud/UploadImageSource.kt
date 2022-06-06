package com.example.gardenguru.data.media.cloud

import com.example.gardenguru.core.exception.ErrorResponseCodeException
import com.example.gardenguru.data.plant.cloud.PhotoDataCloud
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

interface UploadImageSource {

    suspend fun upload(
        token: String,
        type: String,
        file: File
    ): PhotoDataCloud

    class Base @Inject constructor(private val service: UploadImageService) : UploadImageSource {
        override suspend fun upload(
            token: String,
            type: String,
            file: File
        ): PhotoDataCloud {
            val typePart = MultipartBody.Part.createFormData("type", type)
            val filePart = MultipartBody.Part.createFormData(
                "file", file.name,
                RequestBody.create(MediaType.parse("image/*"), file)
            )

            val response = service.fetch(token, typePart, filePart).execute()
            if (response.code() == 201){
                return response.body()!!
            }
            throw ErrorResponseCodeException(response.code(), 201)
        }
    }
}