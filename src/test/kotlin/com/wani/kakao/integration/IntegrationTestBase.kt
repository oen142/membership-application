package com.wani.kakao.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.wani.kakao.common.dto.response.CommonResponse
import com.wani.kakao.common.dto.response.ErrorResponse
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.builder.RequestSpecBuilder
import io.restassured.config.ObjectMapperConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.response.Response
import io.restassured.response.ValidatableResponse
import io.restassured.specification.RequestSpecification
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.operation.preprocess.Preprocessors.*
import org.springframework.restdocs.payload.RequestFieldsSnippet
import org.springframework.restdocs.payload.ResponseFieldsSnippet
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration
import org.springframework.restdocs.snippet.Snippet

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension::class)
class IntegrationTestBase {

    @LocalServerPort
    var port: Int = 0

    @Autowired
    var databaseCleanup: DatabaseCleanup? = null

    @Autowired
    lateinit var objectMapper: ObjectMapper

    lateinit var documentationSpec: RequestSpecification

    @BeforeEach
    fun setup(restDocumentation: RestDocumentationContextProvider) {
        databaseCleanup?.execute() ?: throw RuntimeException("데이터베이스 cleanup이 실패했습니다")

        val mapper = objectMapper

        this.documentationSpec = RequestSpecBuilder()
            .addFilter(documentationConfiguration(restDocumentation))
            .build()

        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(
            ObjectMapperConfig().jackson2ObjectMapperFactory { _, _ -> mapper }
        )


    }

    fun jsonRequestSpec(): RequestSpecification =
        jsonRequestSpec("")

    fun jsonRequestSpec(documentId: String): RequestSpecification =
        jsonRequestSpec(documentId, null, null)

    fun jsonRequestSpec(documentId: String, respDoc: ResponseFieldsSnippet): RequestSpecification =
        jsonRequestSpec(documentId, null, respDoc)

    fun jsonRequestSpec(
        documentId: String,
        reqDoc: RequestFieldsSnippet?,
        respDoc: ResponseFieldsSnippet?
    ): RequestSpecification {
        if (DECLARED_DOCUMENT_IDS.contains(documentId)) {
            throw IllegalArgumentException("Document id '$documentId' is already declared.")
        } else {
            if (documentId.isNotEmpty()) {
                DECLARED_DOCUMENT_IDS.add(documentId)
            }
        }

        val snippets: MutableList<Snippet> = mutableListOf()
        if (reqDoc != null) {
            snippets.add(reqDoc)
        }

        if (respDoc != null) {
            snippets.add(respDoc)
        }


        val baseReqSpec = given(this.documentationSpec).log().all()
            .port(port)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)

        if (documentId.isEmpty()) {
            return baseReqSpec
        } else {
            val uriProcessor = modifyUris().host(DEFAULT_HOST)
            if (DEFAULT_PORT == 0) {
                uriProcessor.removePort()
            } else {
                uriProcessor.port(DEFAULT_PORT)
            }

            return baseReqSpec.filter(
                document(
                    documentId,
                    preprocessRequest(prettyPrint(), uriProcessor),
                    preprocessResponse(prettyPrint()),
                    *snippets.toTypedArray()
                )
            )
        }
    }

    fun <T> expectGenericResponse(
        response: Response,
        status: HttpStatus,
        klass: Class<T>
    ): T {
        val response =
            parseResponse(response.then().assertThat().statusCode(`is`(status.value())))

        val body = response.response

        return objectMapper.convertValue(body, klass)
    }

    @Suppress("UNCHECKED_CAST")
    private fun parseResponse(
        respSpec: ValidatableResponse
    ): CommonResponse<*> {
        val jsonResponse = respSpec.extract().body().asString()

        val map: Map<String, Any> =
            objectMapper.readValue(jsonResponse, Map::class.java) as Map<String, Any>

        val isSuccess: Boolean = map["success"] as Boolean

        return if (isSuccess) {
            val response = map["response"]
            CommonResponse.ok(
                response ?: throw IllegalArgumentException("해당 response를 파싱 할수 없습니다.")
            )
        } else {
            CommonResponse.error(
                statusCode = respSpec.extract().statusCode(),
                errorMessage = respSpec.extract().contentType()
            )
        }
    }

    fun <T> expectResponse(
        response: Response,
        status: HttpStatus,
        klass: Class<T>
    ): T {

        val responseDto = parseResponse(
            respSpec = response.then().assertThat().statusCode(`is`(status.value()))
        )
        val body = responseDto.response
        return objectMapper.convertValue(body, klass)
    }

    fun expectError(
        response: Response,
        status: HttpStatus,
        klass: Class<ErrorResponse>
    ): ErrorResponse {
        val commonResponse = parseResponse(
            respSpec = response.then().assertThat().statusCode(`is`(status.value()))
        )
        val error = commonResponse.error
        return objectMapper.convertValue(error, klass)
    }

    companion object {
        val DECLARED_DOCUMENT_IDS: MutableSet<String> = mutableSetOf()
        const val DEFAULT_HOST = "localhost"
        const val DEFAULT_PORT = 9000


    }
}