package com.entexy.gardenguru.domain.usecases.support

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.domain.repository.SupportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class SendFeedbackUseCase @Inject constructor(private val repository: SupportRepository) {

    fun send(email: String, subject: String, body: String, files: List<File>): Flow<CloudResponse<Unit>> = flow {
        emit(CloudResponse.Loading())
        emit(repository.sendFeedback(email, subject, body, files))
    }.catch {
        emit(CloudResponse.Error(it))
    }
}