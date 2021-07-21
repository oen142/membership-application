package com.wani.kakao.membership.repository

import com.wani.kakao.membership.entity.Membership
import com.wani.kakao.membership.model.MembershipTypeId
import java.util.*

interface MemberShipRepositoryCustom {
    fun findByUserIdAndMembershipId(
        userId: String,
        membershipId: String
    ): Optional<Membership>
}