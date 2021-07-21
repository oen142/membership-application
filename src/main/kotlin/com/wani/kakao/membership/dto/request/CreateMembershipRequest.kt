package com.wani.kakao.membership.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.wani.kakao.membership.entity.Point
import com.wani.kakao.membership.entity.Membership
import com.wani.kakao.membership.model.MembershipType
import com.wani.kakao.membership.model.MembershipTypeId
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@JsonDeserialize
data class CreateMembershipRequest(

    @field:NotNull
    @JsonProperty("membershipId")
    val membershipId: MembershipTypeId,

    @field:NotNull
    @JsonProperty("membershipName")
    val membershipName: String,

    @field:Min(0)
    @JsonProperty("point")
    val point: Int

) {
    fun toMemberShip(userId: String): Membership {
        return Membership(
            id = null,
            userId = userId,
            membershipType = MembershipType.from(membershipId),
            point = Point.wons(point.toLong())
        )
    }
}
