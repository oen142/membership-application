package com.wani.kakao.membership.exception

import com.wani.kakao.common.exception.GeneralHttpException
import org.springframework.http.HttpStatus

class PointRatioMinException (
    override val message: String = "포인트는 최소 0입니다.",
    override val cause: Throwable? = null
) : GeneralHttpException(STATUS, message, cause) {
    companion object {
        val STATUS = HttpStatus.NOT_FOUND
    }
}