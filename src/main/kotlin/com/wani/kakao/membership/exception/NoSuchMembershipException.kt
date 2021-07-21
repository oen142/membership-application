package com.wani.kakao.membership.exception

import com.wani.kakao.common.exception.GeneralHttpException
import org.springframework.http.HttpStatus

class NoSuchMembershipException(
    override val message: String = "해당 멤버쉽을 찾을 수 없습니다.",
    override val cause: Throwable? = null
) : GeneralHttpException(STATUS, message, cause) {
    companion object {
        val STATUS = HttpStatus.NOT_FOUND
    }
}