package com.entexy.gardenguru.data.user.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

interface CreateUserDataSource {

    suspend fun create(id: String): CloudResponse<Unit>

    class Base(private val firestoreUserRef: CollectionReference) : CreateUserDataSource {
        override suspend fun create(id: String): CloudResponse<Unit> {
            //todo replace empty map
            val task = firestoreUserRef.document(id).set(emptyMap<Any, Any>())
            task.await()
            return if (task.exception == null) {
                CloudResponse.Success(Unit)
            } else CloudResponse.Error(task.exception)
        }

    }
}