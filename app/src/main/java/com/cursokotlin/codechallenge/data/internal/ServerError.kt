package com.cursokotlin.codechallenge.data.internal

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ServerError(
    @SerializedName("code") var code: Int = 0,
    @SerializedName("message") var message: String = "",
    @SerializedName("extra") var extra: Map<String, Any>? = null
) : Serializable {
    fun getErrorMessage() = "$code - $message"
}

enum class ServerErrorCode constructor(val code: Int) {
    InvalidGeneric(13),
    BadRequest(400),
    Forbidden(403),
    RequestTimeOut(408),
    InternalServerError(500),
    ServiceUnavailable(503)
}

fun getGenericError(message: String): ServerError {
    return ServerError(ServerErrorCode.InvalidGeneric.code, message)
}