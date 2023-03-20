package com.example.gardenguru.domain.usecases.app

import com.example.gardenguru.data.auth.UserEmailHelper

class UserEmailUseCase constructor(private val emailHelper: UserEmailHelper) {

    fun getEmail() =
        emailHelper.getEmail()

    fun setEmail(value: String) =
        emailHelper.setEmail(value)
}