package com.example.gardenguru.domain.app

import com.example.gardenguru.data.auth.UserEmailHelper
import com.example.gardenguru.data.garden.GardenRepository
import javax.inject.Inject

class UserEmailUseCase constructor(private val emailHelper: UserEmailHelper) {

    fun getEmail() =
        emailHelper.getEmail()

    fun setEmail(value: String) =
        emailHelper.setEmail(value)
}