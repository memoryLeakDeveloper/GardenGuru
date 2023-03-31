package com.entexy.gardenguru.ui.fragments.settings

import androidx.lifecycle.ViewModel
import com.entexy.gardenguru.data.notifcations.NotificationsPref
import com.entexy.gardenguru.data.prefs.UserDataPref
import com.entexy.gardenguru.domain.usecases.user.DeleteUserUseCase
import com.entexy.gardenguru.domain.usecases.user.SignOutUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val notificationsPref: NotificationsPref,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val signOutUserUseCase: SignOutUserUseCase,
    private val userDataPref: UserDataPref
) : ViewModel() {

    fun isNotificationsEnabled() = notificationsPref.get()

    fun changeNotificationsPref(isEnabled: Boolean) = notificationsPref.put(isEnabled)

    suspend fun deleteUser() = deleteUserUseCase.delete()

    suspend fun signOutUser() = signOutUserUseCase.signOut()

    fun clearUserDataPref() = userDataPref.put(null)

}
