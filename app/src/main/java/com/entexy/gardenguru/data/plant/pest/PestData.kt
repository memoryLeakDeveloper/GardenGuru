package com.entexy.gardenguru.data.plant.pest

import android.os.Parcelable
import com.entexy.gardenguru.data.plant.benefit.cloud.BenefitCloud
import com.entexy.gardenguru.data.plant.pest.cloud.PestCloud
import kotlinx.parcelize.Parcelize

@Parcelize
data class PestData(
    var id: String,
    var name: String,
) : Parcelable

fun PestCloud.mapToData(language: String): PestData? {
    if (name == null) return null
    return PestData(
        "",
        localizedName?.get(language) ?: name!!
    )
}
