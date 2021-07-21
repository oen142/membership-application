package com.wani.kakao.membership.application.impl

import com.wani.kakao.common.cache.CacheName
import com.wani.kakao.membership.application.UpdateMembershipService
import com.wani.kakao.membership.dto.request.EarnPointRequest
import com.wani.kakao.membership.exception.NoSuchMembershipException
import com.wani.kakao.membership.model.MembershipType
import com.wani.kakao.membership.repository.MemberShipRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UpdateMembershipServiceImpl(
    val memberShipRepository: MemberShipRepository
) : UpdateMembershipService {

    @Caching(
        evict = [
            CacheEvict(
                cacheNames = arrayOf(CacheName.MEMBERSHIP),
                key = "#userId"
            ),
            CacheEvict(
                cacheNames = arrayOf(CacheName.MEMBERSHIP),
                key = "#userId.concat('').concat(#earnPointRequest.membershipId)"
            )]
    )
    override fun earnPoint(userId: String, earnPointRequest: EarnPointRequest) {
        val membership = memberShipRepository.findByUserIdAndMembershipId(
            userId,
            MembershipType.from(earnPointRequest.membershipId).memberShipId
        ).orElseThrow {
            NoSuchMembershipException(
                "해당 멤버십Id를 가진 유저 찾을수 없습니다." +
                        "userId : $userId , membershipId : ${earnPointRequest.membershipId}"
            )
        }
        membership.earnPoint(earnPointRequest.amount)
    }
}