package com.wani.kakao.common.exception.client

import com.wani.kakao.common.exception.GeneralHttpException
import org.springframework.http.HttpStatus

class IllegalRequestException(
    override val message: String = "Illegal request from client.",
    override val cause: Throwable? = null
) : GeneralHttpException(STATUS, message, cause) {
    companion object {
        val STATUS = HttpStatus.BAD_REQUEST
    }
}
