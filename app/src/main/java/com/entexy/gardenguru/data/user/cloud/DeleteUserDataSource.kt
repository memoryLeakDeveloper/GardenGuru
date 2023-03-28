package com.entexy.gardenguru.data.user.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.utils.bugger
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

interface DeleteUserDataSource {

    suspend fun delete(): CloudResponse<Unit>

    class Base(private val firebaseUser: FirebaseUser) : DeleteUserDataSource {

        override suspend fun delete(): CloudResponse<Unit> {
            val task = firebaseUser.delete().addOnCompleteListener {
                bugger("USER DELETED")
            }
            task.await()
            return if (task.exception == null) {
                CloudResponse.Success(Unit)
            } else CloudResponse.Error(task.exception)
        }

    }
}