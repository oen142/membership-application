package com.wani.kakao.membership.repository

import com.wani.kakao.membership.entity.Membership
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberShipRepository : JpaRepository<Membership, Long>, MemberShipRepositoryCustom {

    fun findByUserId(userId: String): List<Membership>
}