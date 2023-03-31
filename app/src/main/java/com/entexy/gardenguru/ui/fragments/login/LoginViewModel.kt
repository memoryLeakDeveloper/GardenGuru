package com.entexy.gardenguru.ui.fragments.login

import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.core.App
import com.entexy.gardenguru.data.auth.TokenHelper
import com.entexy.gardenguru.data.user.UserData
import com.entexy.gardenguru.domain.usecases.user.CreateUserUseCase
import com.entexy.gardenguru.domain.usecases.user.FetchUserPlantsUseCase
import com.entexy.gardenguru.domain.usecases.user.LoginUserUseCase
import com.entexy.gardenguru.utils.bugger
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val tokenHelper: TokenHelper,
    private val createUserUseCase: CreateUserUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val fetchUserPlantsUseCase: FetchUserPlantsUseCase
) : ViewModel() {

    fun saveNewToken(token: String) = tokenHelper.setToken(token)

    suspend fun createUser(id: String) = createUserUseCase.createUser(id)

    suspend fun loginUser(id: String): String? {
        val loginResponse = loginUserUseCase.login(id)
        loginResponse?.let { user ->
            fetchUserPlantsUseCase.fetch(user.uid)?.let { list ->
                App.user = UserData(user.uid, list)
                bugger(App.user)
            } ?: run {
                return null
            }
        } ?: run {
            return null
        }
        return loginResponse.uid
    }

}