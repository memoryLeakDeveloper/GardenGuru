package com.entexy.gardenguru.ui.fragments.login

import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.data.auth.TokenHelper
import com.entexy.gardenguru.domain.usecases.user.CreateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val tokenHelper: TokenHelper, private val createUserUseCase: CreateUserUseCase) :
    ViewModel() {

    fun saveNewToken(token: String) = tokenHelper.setToken(token)

    suspend fun createUser(id: String) = createUserUseCase.createUser(id)

}