package com.wani.kakao.membership.ui

import com.wani.kakao.common.dto.response.CommonResponse
import com.wani.kakao.membership.application.DeleteMembershipService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class DeleteMembershipController(
    val deleteMembershipService: DeleteMembershipService
) {

    @DeleteMapping("/api/v1/membership/{membershipId}")
    fun deleteMembership(
        @RequestHeader("X-USER-ID") userId: String,
        @PathVariable("membershipId") membershipId: String

    ): ResponseEntity<CommonResponse<Boolean>> {

        deleteMembershipService.deleteMembership(
            userId = userId,
            membershipId = membershipId
        )
        return ResponseEntity.ok().body(CommonResponse.ok(true))
    }
}