package com.wani.kakao.membership.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.wani.kakao.membership.entity.Membership
import com.wani.kakao.membership.entity.QMembership
import com.wani.kakao.membership.model.MembershipTypeId
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import java.util.*

class MemberShipRepositoryImpl(
    val queryFactory: JPAQueryFactory
) : QuerydslRepositorySupport(Membership::class.java), MemberShipRepositoryCustom {

    override fun findByUserIdAndMembershipId(
        userId: String,
        membershipId: String
    ): Optional<Membership> =
        Optional.ofNullable(
            queryFactory.selectFrom(QMembership.membership)
                .where(QMembership.membership.userId.eq(userId))
                .where(QMembership.membership.membershipType.memberShipId.eq(membershipId))
                .fetchFirst()
        )

}