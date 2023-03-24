package com.entexy.gardenguru.data.plant

import androidx.annotation.StringRes
import com.entexy.gardenguru.R

enum class SunRelation(val cloudName: String, @StringRes val id: Int) {
    DirectLight("DIRECT_LIGHT", R.string.direct_sunlight),
    DiffusedLight("DIFFUSED_LIGHT", R.string.diffused_sunlight),
    Penumbra("PENUMBRA", R.string.penumbra)
}