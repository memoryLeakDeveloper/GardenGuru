package com.entexy.gardenguru.domain.usecases.app

import com.entexy.gardenguru.data.auth.UserEmailHelper

class UserEmailUseCase constructor(private val emailHelper: UserEmailHelper) {

    fun getEmail() =
        emailHelper.getEmail()

    fun setEmail(value: String) =
        emailHelper.setEmail(value)
}