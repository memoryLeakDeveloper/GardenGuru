package com.entexy.gardenguru.data.plant.pest.cloud

import com.google.gson.annotations.SerializedName

data class PestCloud(
    var id: String? = null,
    var name: String? = null,
    @SerializedName("localized_name") var localizedName: Map<String, String>? = null,
)