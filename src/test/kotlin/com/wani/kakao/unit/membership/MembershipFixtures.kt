package com.wani.kakao.unit.membership

import com.wani.kakao.membership.entity.Membership
import com.wani.kakao.membership.entity.Point
import com.wani.kakao.membership.model.MembershipStatus
import com.wani.kakao.membership.model.MembershipType
import com.wani.kakao.membership.model.MembershipTypeId

object MembershipFixtures {

    fun aMembership(): Membership =
        Membership(
            id = 1L,
            userId = "test1",
            membershipType = MembershipType.from(MembershipTypeId.cj),
            membershipStatus = MembershipStatus.TRUE,
            point = Point.wons(1000)
        )

    fun bMembership(): Membership =
        Membership(
            id = 2L,
            userId = "test1",
            membershipType = MembershipType.from(MembershipTypeId.shinsegae),
            membershipStatus = MembershipStatus.TRUE,
            point = Point.wons(10000)
        )

}