package com.entexy.gardenguru.data.language

import androidx.annotation.StringRes
import com.entexy.gardenguru.R

enum class Languages(@StringRes val longNameRes: Int, val shortName: String) {
    Russian(R.string.russian, "ru"),
    English(R.string.english, "en")
}