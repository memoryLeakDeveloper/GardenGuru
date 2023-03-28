package com.entexy.gardenguru.data.plant.pest

import android.os.Parcelable
import com.entexy.gardenguru.data.plant.pest.cloud.PestCloud
import kotlinx.parcelize.Parcelize

@Parcelize
data class PestData(
    var id: String,
    var name: String,
    var localizedName: Map<String, String>? = null,
) : Parcelable

fun PestCloud.mapToData(): PestData? {
    if (name == null) return null
    return PestData(
        "",
        name!!,
        localizedName
    )
}
