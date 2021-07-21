package com.wani.kakao.membership.application.impl

import com.wani.kakao.common.cache.CacheName
import com.wani.kakao.membership.application.DeleteMembershipService
import com.wani.kakao.membership.exception.NoSuchMembershipException
import com.wani.kakao.membership.repository.MemberShipRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DeleteMembershipServiceImpl(
    val membershipRepository: MemberShipRepository
) : DeleteMembershipService {


    @Caching(
        evict = [
            CacheEvict(
                cacheNames = arrayOf(CacheName.MEMBERSHIP),
                key = "#userId"
            ),
            CacheEvict(
                cacheNames = arrayOf(CacheName.MEMBERSHIP),
                key = "#userId.concat('').concat(#membershipId)"
            )]
    )
    override fun deleteMembership(userId: String, membershipId: String) {

        val membership = membershipRepository.findByUserIdAndMembershipId(userId, membershipId)
            .orElseThrow {
                throw NoSuchMembershipException(
                    "해당 멤버십을 가진 아이디를 찾을수 없습니다." +
                            "userId : $userId , membershipId : $membershipId"
                )
            }

        membership.deleteMembership()
    }


}