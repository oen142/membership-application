package com.wani.kakao.membership.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.wani.kakao.membership.entity.Membership
import java.time.LocalDateTime

@JsonTypeInfo(
    use = JsonTypeInfo.Id.CLASS,
    include = JsonTypeInfo.As.PROPERTY,
    property = "@class"
)
@JsonSerialize
data class CreateMembershipResponse(

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
) {
    companion object {

        fun from(src: Membership): CreateMembershipResponse =
            CreateMembershipResponse(
                seq = src.id ?: 0L,
                membershipId = src.membershipType.memberShipId,
                membershipName = src.membershipType.memberShipName,
                startDate = src.startDate,
                membershipStatus = src.membershipStatus?.toValue() ?: "N",
                point = src.point.toInt()
            )

    }
}
