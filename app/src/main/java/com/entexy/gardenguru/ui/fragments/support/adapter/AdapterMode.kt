package com.entexy.gardenguru.ui.fragments.support.adapter

sealed class AdapterMode {
    object Default : AdapterMode()
    object Select : AdapterMode()
    object SelectException : AdapterMode()

}

fun changeMode(mode: AdapterMode): AdapterMode {
    return if (mode == AdapterMode.Default)
        AdapterMode.Select
    else
        AdapterMode.Default
}