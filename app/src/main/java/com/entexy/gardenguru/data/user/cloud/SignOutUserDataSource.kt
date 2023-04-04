package com.entexy.gardenguru.data.user.cloud

import com.entexy.gardenguru.core.exception.CloudResponse
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

interface SignOutUserDataSource {

    suspend fun signOut(): CloudResponse<Unit>

    class Base : SignOutUserDataSource {

        override suspend fun signOut(): CloudResponse<Unit> {
            Firebase.auth.signOut()
            return CloudResponse.Success(Unit)
        }
    }
}