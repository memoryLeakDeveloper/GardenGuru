package com.entexy.gardenguru.data.support

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.data.support.cloud.SendFeedbackDataSource
import com.entexy.gardenguru.domain.repository.SupportRepository
import java.io.File
import javax.inject.Inject

class SupportRepositoryImpl @Inject constructor(
    private val sendFeedbackDataSource: SendFeedbackDataSource
) : SupportRepository {

    override suspend fun sendFeedback(
        email: String,
        subject: String,
        body: String,
        files: List<File>,
    ): CloudResponse<Unit> =
        sendFeedbackDataSource.sendFeedback(email, subject, body, files)
}