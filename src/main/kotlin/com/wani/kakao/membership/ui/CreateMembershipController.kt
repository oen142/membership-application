package com.wani.kakao.membership.ui

import com.wani.kakao.common.dto.response.CommonResponse
import com.wani.kakao.membership.application.CreateMembershipService
import com.wani.kakao.membership.dto.request.CreateMembershipRequest
import com.wani.kakao.membership.dto.response.CreateMembershipResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class CreateMembershipController(
    val createMembershipService: CreateMembershipService
) {

    @PostMapping("/api/v1/membership")
    fun createMemberShip(
        @RequestHeader("X-USER-ID") userId: String,
        @Valid @RequestBody membershipRequest: CreateMembershipRequest
    ): ResponseEntity<CommonResponse<CreateMembershipResponse>> {
        val response = createMembershipService.createMemberShip(userId, membershipRequest)

        return ResponseEntity.ok().body(CommonResponse.ok(response))
    }
}