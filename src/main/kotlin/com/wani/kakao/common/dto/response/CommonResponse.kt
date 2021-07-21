package com.wani.kakao.common.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class CommonResponse<T> private constructor(

    @JsonProperty("success")
    val success: Boolean,

    @JsonProperty("response")
    val response: T?,

    @JsonProperty("error")
    val error: ErrorResponse?
) {


    companion object {
        fun <T> ok(payload: T) = CommonResponse(
            success = true,
            response = payload,
            error = null
        )

        fun error(statusCode: Int, errorMessage: String): CommonResponse<ErrorResponse> =
            CommonResponse(
                success = false,
                response = null,
                error = ErrorResponse(
                    message = errorMessage,
                    status = statusCode
                )
            )

    }

}