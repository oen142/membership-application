package com.wani.kakao.membership.exception

import com.wani.kakao.common.exception.GeneralHttpException
import org.springframework.http.HttpStatus
import java.lang.RuntimeException

class NoSuchMembershipTypeException(
    override val message: String = "해당 파라미터를 가진 멤버쉽 타입을 찾을수 없습니다.",
    override val cause: Throwable? = null
) : GeneralHttpException(STATUS, message, cause) {
    companion object {
        val STATUS = HttpStatus.NOT_FOUND
    }
}

