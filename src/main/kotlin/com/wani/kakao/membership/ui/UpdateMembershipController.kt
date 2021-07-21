package com.wani.kakao.membership.ui

import com.wani.kakao.common.dto.response.CommonResponse
import com.wani.kakao.membership.application.UpdateMembershipService
import com.wani.kakao.membership.dto.request.EarnPointRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class UpdateMembershipController(
    val updateMembershipService: UpdateMembershipService
) {

    @PutMapping("/api/v1/membership/point")
    fun earnPoint(
        @RequestHeader("X-USER-ID") userId: String,
        @RequestBody earnPointRequest: EarnPointRequest
    ): ResponseEntity<CommonResponse<Boolean>> {
        updateMembershipService.earnPoint(userId, earnPointRequest)

        return ResponseEntity.ok().body(CommonResponse.ok(true))
    }
}