package com.entexy.gardenguru.domain.repository

import com.entexy.gardenguru.core.exception.CloudResponse
import com.google.firebase.auth.FirebaseUser

interface UserRepository {

    suspend fun loginUser(idToken: String): FirebaseUser?

    suspend fun createUser(id: String): CloudResponse<Unit>

    suspend fun deleteUser(): CloudResponse<Unit>

    suspend fun signOutUser(): CloudResponse<Unit>

    suspend fun fetchAllPlants(uid: String): List<String>?

}