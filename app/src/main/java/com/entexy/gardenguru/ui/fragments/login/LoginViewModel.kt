package com.entexy.gardenguru.ui.fragments.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.entexy.gardenguru.core.App
import com.entexy.gardenguru.data.prefs.UserDataPref
import com.entexy.gardenguru.data.user.UserData
import com.entexy.gardenguru.domain.usecases.user.CreateUserUseCase
import com.entexy.gardenguru.domain.usecases.user.LoginUserUseCase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val userDataPref: UserDataPref
) : ViewModel() {

    suspend fun isUserAuthorized() =
        viewModelScope.async { Firebase.auth.currentUser?.let { return@async true } ?: run { return@async false } }.await()

    suspend fun createUser(id: String) = createUserUseCase.createUser(id)

    suspend fun loginUser(id: String): String? {
        loginUserUseCase.login(id)?.let { user ->
            App.user = UserData(user.uid)
            userDataPref.put(UserData(user.uid))
            return user.uid
        } ?: run {
            return null
        }
    }

}