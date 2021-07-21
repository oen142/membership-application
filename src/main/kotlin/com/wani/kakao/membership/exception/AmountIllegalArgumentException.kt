package com.wani.kakao.membership.exception

import com.wani.kakao.common.exception.GeneralHttpException
import org.springframework.http.HttpStatus

class AmountIllegalArgumentException(
    override val message: String = "적립할수 없는 금액입니다.",
    override val cause: Throwable? = null
) : GeneralHttpException(STATUS, message, cause) {
    companion object {
        val STATUS = HttpStatus.BAD_REQUEST
    }
}