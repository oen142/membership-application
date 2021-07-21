package com.wani.kakao.membership.exception

import com.wani.kakao.common.exception.GeneralHttpException
import org.springframework.http.HttpStatus

class ExistDeletedMembershipException(
    override val message: String = "해당 멤버쉽은 이미 삭제된 멤버십입니다.",
    override val cause: Throwable? = null
) : GeneralHttpException(STATUS, message, cause) {
    companion object {
        val STATUS = HttpStatus.NOT_FOUND
    }
}