package com.example.gardenguru.data.plant.cloud

import com.example.gardenguru.core.Base

data class PhotoDataCloud(
    var id: String,
    var thumbnail: String,
    var file: String
) : Base.DataObject