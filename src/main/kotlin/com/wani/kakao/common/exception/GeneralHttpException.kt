package com.wani.kakao.common.exception

import com.wani.kakao.common.exception.generic.ResourceNotFoundException
import com.wani.kakao.common.exception.generic.UnauthenticatedException
import org.springframework.http.HttpStatus

open class GeneralHttpException protected constructor(
    val httpStatus: HttpStatus,
    override val message: String = "",
    override val cause: Throwable? = null
) : RuntimeException(message, cause) {

    companion object {
        fun createPlain(httpStatus: HttpStatus, message: String, cause: Throwable? = null) =
            GeneralHttpException(httpStatus, message, cause)

        fun create(httpStatus: HttpStatus, resourceName: String = "", cause: Throwable? = null) = when (httpStatus) {
            HttpStatus.NOT_FOUND -> ResourceNotFoundException(resourceName, cause)
            HttpStatus.UNAUTHORIZED -> UnauthenticatedException(cause = cause)
            else -> if (resourceName.isEmpty()) {
                GeneralHttpException(httpStatus, httpStatus.reasonPhrase, cause)
            } else {
                GeneralHttpException(httpStatus, httpStatus.reasonPhrase + ": $resourceName", cause)
            }
        }
    }
}