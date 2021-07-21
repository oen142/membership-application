package com.wani.kakao.membership.application.impl

import com.wani.kakao.common.cache.CacheName
import com.wani.kakao.membership.application.CreateMembershipService
import com.wani.kakao.membership.dto.request.CreateMembershipRequest
import com.wani.kakao.membership.dto.response.CreateMembershipResponse
import com.wani.kakao.membership.repository.MemberShipRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreateMembershipServiceImpl(
    val memberShipRepository: MemberShipRepository,
) : CreateMembershipService {

    @Caching(
        evict = [
            CacheEvict(
                cacheNames = arrayOf(CacheName.MEMBERSHIP),
                key = "#userId"
            ),
            CacheEvict(
                cacheNames = arrayOf(CacheName.MEMBERSHIP),
                key = "#userId.concat('').concat(#membershipRequest.membershipId)"
            )]
    )
    override fun createMemberShip(
        userId: String,
        membershipRequest: CreateMembershipRequest
    ): CreateMembershipResponse {
        val memberShip = membershipRequest.toMemberShip(userId)

        memberShipRepository.save(memberShip)

        return CreateMembershipResponse.from(memberShip)
    }
}