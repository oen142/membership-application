package com.wani.kakao.membership.model

import com.wani.kakao.membership.entity.PointRatio
import com.wani.kakao.membership.exception.NoSuchMembershipTypeException
import com.wani.kakao.membership.model.MembershipTypeId.*
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class MembershipType private constructor(
    @Column(name = "membership_id", nullable = false)
    val memberShipId: String,
    @Column(name = "membership_name", nullable = false)
    val memberShipName: String,
    @Column(name = "point_ratio", nullable = false)
    val pointRatio: PointRatio = PointRatio.DEFAULT_POINT_RATIO
) {

    companion object {
        fun from(membershipTypeId: MembershipTypeId): MembershipType =
            when (membershipTypeId) {
                spc -> MembershipType("spc", "happypoint")
                shinsegae -> MembershipType("shinsegae", "shinsegaepoint")
                cj -> MembershipType("cj", "cjone")
            }
    }


}

enum class MembershipTypeId {
    spc, shinsegae, cj;

    companion object {
        fun findMembershipTypeId(typeId: String) {
            values().firstOrNull {
                it.name == typeId
            } ?: throw NoSuchMembershipTypeException(
                "해당하는 멤버십을 찾을 수 없습니다." +
                        "membershipId : $typeId"
            )
        }
    }
}