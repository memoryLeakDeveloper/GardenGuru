package com.entexy.gardenguru.core.exception

sealed class CloudResponse<T> {
    class Success<T>(val result: T) : CloudResponse<T>()
    class Error<Nothing>(val exception: java.lang.Exception?) : CloudResponse<Nothing>()
}