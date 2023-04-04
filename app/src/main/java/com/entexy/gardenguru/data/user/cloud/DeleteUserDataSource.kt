package com.entexy.gardenguru.data.user.cloud

import com.entexy.gardenguru.core.App
import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.utils.bugger
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface DeleteUserDataSource {

    suspend fun delete(): CloudResponse<Unit>

    class Base : DeleteUserDataSource {

        override suspend fun delete(): CloudResponse<Unit> {
            val currentUser = Firebase.auth.currentUser ?: return CloudResponse.Error(Exception())
            App.firestoreUsersRef.document(Firebase.auth.currentUser!!.uid).delete().await()
            val task = currentUser.delete()
            task.await()
            return if (task.exception == null) {
                CloudResponse.Success(Unit)
            } else CloudResponse.Error(task.exception)
        }

    }
}