package com.entexy.gardenguru.core.exception

class ErrorResponseCodeException(private val errorCode: Int, private val needCode: Int) : Exception() {
    override val message: String
        get() = "Response code exception: errorCode = $errorCode, needCode = $needCode"
}