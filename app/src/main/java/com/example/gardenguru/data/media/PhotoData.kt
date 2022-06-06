package com.example.gardenguru.data.media

import com.example.gardenguru.core.Base

data class PhotoData(
    var id: String,
    var thumbnail: String,
    var file: String
) : Base.DataObject