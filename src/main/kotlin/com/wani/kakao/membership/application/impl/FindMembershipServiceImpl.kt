package com.wani.kakao.membership.application.impl

import com.wani.kakao.common.cache.CacheName
import com.wani.kakao.membership.application.FindMembershipService
import com.wani.kakao.membership.dto.response.FindMembershipResponse
import com.wani.kakao.membership.entity.Membership
import com.wani.kakao.membership.exception.NoSuchMembershipException
import com.wani.kakao.membership.repository.MemberShipRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FindMembershipServiceImpl(
    val memberShipRepository: MemberShipRepository
) : FindMembershipService {


    @Cacheable(value = [CacheName.MEMBERSHIP], key = "#userId")
    override fun findMemberShipsByUserId(userId: String): List<FindMembershipResponse> {
        val memberships = memberShipRepository.findByUserId(userId)

        return memberships.map {
            FindMembershipResponse.from(it)
        }.toList()
    }

    @Cacheable(value = [CacheName.MEMBERSHIP], key = "#userId.concat('').concat(#membershipId)")
    override fun findMembershipByUserIdAndMembershipId(
        userId: String,
        membershipId: String
    ): FindMembershipResponse {
        val membership =
            memberShipRepository.findByUserIdAndMembershipId(userId, membershipId).orElseThrow {
                NoSuchMembershipException(
                    "해당 멤버십Id를 가진 유저를 찾을수 없습니다." +
                            "userId : $userId , membershipId : $membershipId"
                )
            }

        return FindMembershipResponse.from(membership)
    }

}