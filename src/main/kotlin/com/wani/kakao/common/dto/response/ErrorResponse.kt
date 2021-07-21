package com.wani.kakao.common.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorResponse(

    @JsonProperty("message")
    val message: String,

    @JsonProperty("status")
    val status: Int
)
