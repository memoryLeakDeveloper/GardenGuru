package com.entexy.gardenguru.data.support.cloud

import com.entexy.gardenguru.core.SupportApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SendFeedbackService {

    @POST("${SupportApi.apiVersion}/feedbacks/")
    suspend fun sendFeedback(
        @Body body: FeedbackCloudData
    ): Response<Void>
}