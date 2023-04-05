package com.entexy.gardenguru.domain.repository

import com.entexy.gardenguru.core.exception.CloudResponse
import java.io.File

interface SupportRepository {

    suspend fun sendFeedback(email: String, subject: String, body: String, files: List<File>): CloudResponse<Unit>

}