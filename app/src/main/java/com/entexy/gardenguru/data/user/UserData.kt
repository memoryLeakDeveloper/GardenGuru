package com.entexy.gardenguru.data.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(
    val userId: String
): Parcelable