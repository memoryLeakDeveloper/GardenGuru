package com.entexy.gardenguru.ui.fragments.settings.support

sealed class AdapterMode {
    object Default : AdapterMode()
    object Select : AdapterMode()
}

fun changeMode(mode: AdapterMode): AdapterMode {
    return if (mode == AdapterMode.Default)
        AdapterMode.Select
    else
        AdapterMode.Default
}