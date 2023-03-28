package com.entexy.gardenguru.data.plant.benefit

import android.os.Parcelable
import com.entexy.gardenguru.data.plant.benefit.cloud.BenefitCloud
import kotlinx.parcelize.Parcelize

@Parcelize
data class BenefitData(var id: String, val name: String, val localizedName: Map<String, String>? = null) : Parcelable

fun BenefitCloud.mapToData(): BenefitData? {
    return if (name == null || id == null) null
    else BenefitData(
        id!!,
        name!!,
        localizedName = localizedName
    )
}