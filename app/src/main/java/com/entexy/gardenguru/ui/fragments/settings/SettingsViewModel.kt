package com.entexy.gardenguru.ui.fragments.settings

import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.data.notifcations.NotificationsPref
import com.entexy.gardenguru.domain.usecases.user.DeleteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val notificationsPref: NotificationsPref,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    fun isNotificationsEnabled() = notificationsPref.get()

    fun changeNotificationsPref(isEnabled: Boolean) = notificationsPref.put(isEnabled)

    suspend fun deleteUser() = deleteUserUseCase.delete()

}
