package com.wani.kakao.common.exception.generic

import com.wani.kakao.common.exception.GeneralHttpException
import org.springframework.http.HttpStatus

class UnauthenticatedException(
    override val message: String = "You are not authenticated.",
    override val cause: Throwable? = null
) : GeneralHttpException(STATUS, message, cause) {

    companion object {
        val STATUS = HttpStatus.UNAUTHORIZED
    }
}