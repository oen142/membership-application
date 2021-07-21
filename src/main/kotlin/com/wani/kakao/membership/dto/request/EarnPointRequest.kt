package com.wani.kakao.membership.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.wani.kakao.membership.model.MembershipTypeId
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@JsonDeserialize
data class EarnPointRequest(

    @field:NotNull
    @JsonProperty("membershipId")
    val membershipId: MembershipTypeId,

    @field:Min(0)
    @JsonProperty("amount")
    val amount: Int
)
