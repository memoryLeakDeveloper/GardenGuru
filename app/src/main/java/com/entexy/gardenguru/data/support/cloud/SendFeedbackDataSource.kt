package com.entexy.gardenguru.data.support.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

interface SendFeedbackDataSource {

    suspend fun sendFeedback(
        email: String,
        subject: String,
        body: String,
        files: List<File>
    ): CloudResponse<Unit>

    class Base(private val sendFeedbackService: SendFeedbackService, private val sendMediaService: SendMediaService) : SendFeedbackDataSource {
        override suspend fun sendFeedback(
            email: String,
            subject: String,
            body: String,
            files: List<File>
        ): CloudResponse<Unit> {

            val mediaIds = mutableListOf<Int>()
            files.forEach {
                val mediaResponse = sendMediaService.sendMedia(
                    MultipartBody.Part.createFormData(
                    "file",
                    it.name,
                    RequestBody.create(MediaType.parse("image/*"), it)
                ))

                if (mediaResponse.isSuccessful){
                    mediaIds.add(mediaResponse.body()!!.id)
                }
            }

            val response = sendFeedbackService.sendFeedback(
                FeedbackCloudData(
                    "GardenGuru",
                    subject,
                    body,
                    ClientCloudData(email),
                    mediaIds
                )
            )

            if (response.isSuccessful) {
                return CloudResponse.Success(Unit)
            }
            return CloudResponse.Error(java.lang.IllegalArgumentException(response.errorBody().toString()))
        }
    }
}