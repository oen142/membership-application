package com.wani.kakao.membership.application

import com.wani.kakao.membership.dto.request.CreateMembershipRequest
import com.wani.kakao.membership.dto.response.CreateMembershipResponse

interface CreateMembershipService {

    fun createMemberShip(userId: String, membershipRequest: CreateMembershipRequest): CreateMembershipResponse
}