package com.wani.kakao.integration.membership.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.wani.kakao.membership.entity.Membership
import java.time.LocalDateTime

@JsonSerialize
data class CreateMembershipTestResponse(

    @JsonProperty("seq")
    val seq: Long,

    @JsonProperty("membershipId")
    val membershipId: String,

    @JsonProperty("membershipName")
    val membershipName: String,

    @JsonProperty("startDate")
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    val startDate: LocalDateTime,

    @JsonProperty("membershipStatus")
    val membershipStatus: String,

    @JsonProperty("point")
    val point: Int
)