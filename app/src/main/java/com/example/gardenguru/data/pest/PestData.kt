package com.example.gardenguru.data.pest

import android.net.Uri
import com.example.gardenguru.core.Base

data class PestData(
    var name: String,
    var description: String,
    var photo: Uri
) : Base.DataObject
