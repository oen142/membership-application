package com.wani.kakao.membership.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.wani.kakao.membership.entity.Membership
import java.io.IOException
import java.time.LocalDateTime

@JsonTypeInfo(
    use = JsonTypeInfo.Id.CLASS,
    include = JsonTypeInfo.As.PROPERTY,
    property = "@class"
)
@JsonSerialize
data class FindMembershipResponse(
    @JsonProperty("seq")
    val seq: Long,

    @JsonProperty("membershipId")
    val membershipId: String,

    @JsonProperty("userId")
    val userId: String,

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

        fun from(src: Membership): FindMembershipResponse =
            FindMembershipResponse(
                seq = src.id ?: 0L,
                membershipId = src.membershipType.memberShipId,
                userId = src.userId,
                membershipName = src.membershipType.memberShipName,
                startDate = src.startDate,
                membershipStatus = src.membershipStatus?.toValue() ?: "N",
                point = src.point.toInt()
            )
    }
}


