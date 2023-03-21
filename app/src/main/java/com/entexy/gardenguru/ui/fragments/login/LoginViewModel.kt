package com.entexy.gardenguru.ui.fragments.login

import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.data.auth.TokenHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val tokenHelper: TokenHelper.Base) : ViewModel() {

    fun saveNewToken(token: String) = tokenHelper.setToken(token)

}