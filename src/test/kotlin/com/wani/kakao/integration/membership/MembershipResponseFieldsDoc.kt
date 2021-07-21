package com.wani.kakao.integration.membership

import com.wani.kakao.integration.BasicDocsField
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.payload.ResponseFieldsSnippet

object MembershipResponseFieldsDoc {

    fun createMembershipResponseFieldDoc(): ResponseFieldsSnippet {
        return responseFields(BasicDocsField.basicFieldDoc() + createMembershipResponseFields("response"))
    }

    private fun createMembershipResponseFields(prefix: String): List<FieldDescriptor> =
        listOf(
            fieldWithPath("$prefix.seq")
                .type(JsonFieldType.NUMBER)
                .description("멤버십 index"),
            fieldWithPath("$prefix.membershipId")
                .type(JsonFieldType.STRING)
                .description("멤버십 아이디"),
            fieldWithPath("$prefix.membershipName")
                .type(JsonFieldType.STRING)
                .description("멤버십 이름"),
            fieldWithPath("$prefix.startDate")
                .type(JsonFieldType.STRING)
                .description("멤버십 시작날짜"),
            fieldWithPath("$prefix.membershipStatus")
                .type(JsonFieldType.STRING)
                .description("멤버십 상태"),
            fieldWithPath("$prefix.point")
                .type(JsonFieldType.NUMBER)
                .description("포인트")

        )


    fun findOneMembershipResponseFieldDoc(): ResponseFieldsSnippet {
        return responseFields(BasicDocsField.basicFieldDoc() + findOneMembershipResponseFields("response"))
    }

    private fun findOneMembershipResponseFields(prefix: String): List<FieldDescriptor> =
        listOf(
            fieldWithPath("$prefix.seq")
                .type(JsonFieldType.NUMBER)
                .description("멤버십 index"),
            fieldWithPath("$prefix.userId")
                .type(JsonFieldType.STRING)
                .description("유저 아이디"),
            fieldWithPath("$prefix.membershipId")
                .type(JsonFieldType.STRING)
                .description("멤버십 아이디"),
            fieldWithPath("$prefix.membershipName")
                .type(JsonFieldType.STRING)
                .description("멤버십 이름"),
            fieldWithPath("$prefix.startDate")
                .type(JsonFieldType.STRING)
                .description("멤버십 시작날짜"),
            fieldWithPath("$prefix.membershipStatus")
                .type(JsonFieldType.STRING)
                .description("멤버십 상태"),
            fieldWithPath("$prefix.point")
                .type(JsonFieldType.NUMBER)
                .description("포인트")

        )

    fun findMembershipsResponseFieldDoc(): ResponseFieldsSnippet {
        return responseFields(BasicDocsField.basicListFieldDoc() + findMembershipsResponseFields("response[]"))
    }

    private fun findMembershipsResponseFields(prefix: String): List<FieldDescriptor> =
        listOf(
            fieldWithPath("$prefix.seq")
                .type(JsonFieldType.NUMBER)
                .description("멤버십 index"),
            fieldWithPath("$prefix.userId")
                .type(JsonFieldType.STRING)
                .description("유저 아이디"),
            fieldWithPath("$prefix.membershipId")
                .type(JsonFieldType.STRING)
                .description("멤버십 아이디"),
            fieldWithPath("$prefix.membershipName")
                .type(JsonFieldType.STRING)
                .description("멤버십 이름"),
            fieldWithPath("$prefix.startDate")
                .type(JsonFieldType.STRING)
                .description("멤버십 시작날짜"),
            fieldWithPath("$prefix.membershipStatus")
                .type(JsonFieldType.STRING)
                .description("멤버십 상태"),
            fieldWithPath("$prefix.point")
                .type(JsonFieldType.NUMBER)
                .description("포인트")

        )


    fun earnPointResponseFieldDoc(): ResponseFieldsSnippet {
        return responseFields(BasicDocsField.responseBooleanFieldDoc())
    }


    fun deleteMembershipResponseFieldDoc(): ResponseFieldsSnippet {
        return responseFields(BasicDocsField.responseBooleanFieldDoc())
    }


}