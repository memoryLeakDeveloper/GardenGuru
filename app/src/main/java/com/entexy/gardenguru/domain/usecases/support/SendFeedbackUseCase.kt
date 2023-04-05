package com.entexy.gardenguru.domain.usecases.support

import com.entexy.gardenguru.domain.repository.SupportRepository
import java.io.File
import javax.inject.Inject

class SendFeedbackUseCase @Inject constructor(private val repository: SupportRepository) {

    fun send(
        email: String,
        subject: String?,
        body: String,
        files: List<File>?,
        onCompleteLambda: (success: Boolean, message: String?) -> Unit
    ) {
        repository.sendFeedback(email, subject, body, files, onCompleteLambda)
    }

}