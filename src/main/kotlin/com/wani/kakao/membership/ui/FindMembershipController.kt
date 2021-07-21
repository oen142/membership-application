package com.wani.kakao.membership.ui

import com.wani.kakao.common.dto.response.CommonResponse
import com.wani.kakao.membership.application.FindMembershipService
import com.wani.kakao.membership.dto.response.FindMembershipResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class FindMembershipController(
    val findMembershipService: FindMembershipService
) {

    @GetMapping("/api/v1/membership")
    fun findMemberShipsByMemberId(
        @RequestHeader("X-USER-ID") userId: String
    ): ResponseEntity<CommonResponse<List<FindMembershipResponse>>> {

        val response = findMembershipService.findMemberShipsByUserId(userId)

        return ResponseEntity.ok().body(CommonResponse.ok(response))
    }

    @GetMapping("/api/v1/membership/{membershipId}")
    fun findMembershipByMembershipId(
        @RequestHeader("X-USER-ID") userId: String,
        @PathVariable("membershipId") membershipId: String
    ): ResponseEntity<CommonResponse<FindMembershipResponse>> {
        val response =  findMembershipService.findMembershipByUserIdAndMembershipId(userId , membershipId)
        return ResponseEntity.ok().body(CommonResponse.ok(response))
    }
}