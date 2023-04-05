package com.entexy.gardenguru.domain.repository

import java.io.File

interface SupportRepository {

    fun sendFeedback(
        email: String,
        subject: String?,
        body: String,
        files: List<File>?,
        onCompleteLambda: (success: Boolean, message: String?) -> Unit
    )

}