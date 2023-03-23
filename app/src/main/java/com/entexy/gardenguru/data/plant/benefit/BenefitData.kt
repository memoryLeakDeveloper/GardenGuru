package com.entexy.gardenguru.data.plant.benefit

import android.os.Parcelable
import com.entexy.gardenguru.data.plant.benefit.cloud.BenefitCloud
import com.entexy.gardenguru.data.plant.pest.PestData
import kotlinx.parcelize.Parcelize

@Parcelize
data class BenefitData(var id: String, val name: String) : Parcelable

fun BenefitCloud.mapToData(language: String): BenefitData? {
    return if (name == null || id == null) null
    else BenefitData(
        id!!,
        localizedName?.get(language) ?: name!!
    )
}