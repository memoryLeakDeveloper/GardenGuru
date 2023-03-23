package com.entexy.gardenguru.data.plant.benefit.cloud

import com.google.gson.annotations.SerializedName

data class BenefitCloud(
    var id: String? = null,
    var name: String? = null,
    @SerializedName("localized_name") var localizedName: Map<String, String>? = null,
)