package com.wani.kakao.membership.application

import com.wani.kakao.membership.dto.response.FindMembershipResponse
import com.wani.kakao.membership.entity.Membership

interface FindMembershipService {

    fun findMemberShipsByUserId(userId: String): List<FindMembershipResponse>

    fun findMembershipByUserIdAndMembershipId(
        userId: String,
        membershipId: String
    ): FindMembershipResponse
}
