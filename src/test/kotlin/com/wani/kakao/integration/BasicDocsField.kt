package com.wani.kakao.integration

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.payload.ResponseFieldsSnippet

object BasicDocsField {

    fun basicFieldDoc(): List<FieldDescriptor> {
        return listOf(
            fieldWithPath("success")
                .type(JsonFieldType.BOOLEAN)
                .description("해당 타입은 TRUE , FASLE로 성공 실패를 나타냅니다."),
            fieldWithPath("response")
                .type(JsonFieldType.OBJECT)
                .description("만약 success가 TRUE이면 해당 내용이 채워집니다.")
                .optional(),
            fieldWithPath("error")
                .type(JsonFieldType.OBJECT)
                .description("만약 success가 FALSE이면 해당 내용이 채워집니다.")
                .optional()
        )
    }

    fun basicListFieldDoc(): List<FieldDescriptor> {
        return listOf(
            fieldWithPath("success")
                .type(JsonFieldType.BOOLEAN)
                .description("해당 타입은 TRUE , FASLE로 성공 실패를 나타냅니다."),
            subsectionWithPath("response[]")
                .type(JsonFieldType.ARRAY)
                .description("만약 success가 TRUE이면 해당 내용이 채워집니다.")
                .optional(),
            fieldWithPath("error")
                .type(JsonFieldType.OBJECT)
                .description("만약 success가 FALSE이면 해당 내용이 채워집니다.")
                .optional()
        )
    }

    fun responseBooleanFieldDoc(): List<FieldDescriptor> {
        return listOf(
            fieldWithPath("success")
                .type(JsonFieldType.BOOLEAN)
                .description("해당 타입은 TRUE , FASLE로 성공 실패를 나타냅니다."),
            fieldWithPath("response")
                .type(JsonFieldType.BOOLEAN)
                .description("만약 수정이나 삭제가 성공하면 boolean으로 나타내어 집니다")
                .optional(),
            fieldWithPath("error")
                .type(JsonFieldType.OBJECT)
                .description("만약 success가 FALSE이면 해당 내용이 채워집니다.")
                .optional()
        )
    }

    fun errorResponseFieldDoc(): ResponseFieldsSnippet {
        val fields = listOf(
            fieldWithPath("body.message")
                .type(JsonFieldType.STRING)
                .description("해당 에러가 발생할 경우 메시지를 표기해줍니다."),
            fieldWithPath("body.status")
                .type(JsonFieldType.NUMBER)
                .description("해당 에러가 발생할 경우 http status 코드가 나옵ㄴ디ㅏ.")
        )
        return responseFields(basicFieldDoc() + fields)
    }
}