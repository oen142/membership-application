package com.wani.kakao.integration.membership

import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.RequestFieldsSnippet

object MembershipRequestFieldsDoc {

    fun createMembershipRequestFieldsDoc(): RequestFieldsSnippet =
        requestFields(
            fieldWithPath("membershipId")
                .type(JsonFieldType.STRING)
                .description("멤버십 아이디"),
            fieldWithPath("membershipName")
                .type(JsonFieldType.STRING)
                .description("멤버십 아이디"),
            fieldWithPath("point")
                .type(JsonFieldType.NUMBER)
                .description("초기 포인트")
        )

    fun earnPointRequestFieldsDoc(): RequestFieldsSnippet =
        requestFields(
            fieldWithPath("membershipId")
                .type(JsonFieldType.STRING)
                .description("멤버십 아이디"),
            fieldWithPath("amount")
                .type(JsonFieldType.NUMBER)
                .description("사용 금액")
        )


}