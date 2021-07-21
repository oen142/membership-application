package com.wani.kakao.common.exception.generic

import com.wani.kakao.common.exception.GeneralHttpException
import org.springframework.http.HttpStatus

class ResourceNotFoundException(
    resourceName: String,
    override val cause: Throwable? = null
) : GeneralHttpException(
    httpStatus = HttpStatus.NOT_FOUND,
    message = if (resourceName.isEmpty()) {
        "Resource not found"
    } else {
        "Resource '$resourceName' is not fount"
    },
    cause = cause
)