package com.entexy.gardenguru.data.plant.benefit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BenefitData(val id: String, val name: String) : Parcelable