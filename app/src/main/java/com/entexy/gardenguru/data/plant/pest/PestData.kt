package com.entexy.gardenguru.data.plant.pest

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PestData(
    var id: String,
    var name: String
): Parcelable
