package com.wani.kakao.integration

import com.wani.kakao.membership.dto.request.CreateMembershipRequest
import com.wani.kakao.membership.dto.request.EarnPointRequest
import io.restassured.http.Header
import io.restassured.response.Response
import org.springframework.restdocs.payload.RequestFieldsSnippet
import org.springframework.restdocs.payload.ResponseFieldsSnippet

class MemberShipTestBase : IntegrationTestBase() {

    fun createMembership(
        request: CreateMembershipRequest,
        header: Header,
        documentId: String,
        reqDoc: RequestFieldsSnippet?,
        respDoc: ResponseFieldsSnippet?
    ): Response {

        return jsonRequestSpec(documentId, reqDoc, respDoc)
            .`when`()
            .header(header)
            .body(request)
            .post("/api/v1/membership")
    }

    fun createMembershipRequestMap(
        request: Map<String, String>,
        header: Header,
        documentId: String,
        reqDoc: RequestFieldsSnippet?,
        respDoc: ResponseFieldsSnippet?
    ): Response {

        return jsonRequestSpec(documentId, reqDoc, respDoc)
            .`when`()
            .header(header)
            .body(request)
            .post("/api/v1/membership")
    }

    fun findMemberships(
        request: CreateMembershipRequest,
        header: Header,
        documentId: String,
        reqDoc: RequestFieldsSnippet,
        respDoc: ResponseFieldsSnippet
    ): Response {

        return jsonRequestSpec(documentId, reqDoc, respDoc)
            .`when`()
            .header(header)
            .body(request)
            .get("/api/v1/membership")
    }

    fun findMemberships(
        header: Header,
        documentId: String,
        respDoc: ResponseFieldsSnippet?
    ): Response {

        return jsonRequestSpec(documentId, null, respDoc)
            .`when`()
            .header(header)
            .get("/api/v1/membership")
    }

    fun findOneMembership(
        membershipTypeIdPath: String,
        header: Header,
        documentId: String,
        respDoc: ResponseFieldsSnippet?
    ): Response {

        return jsonRequestSpec(documentId, null, respDoc)
            .`when`()
            .header(header)
            .get("/api/v1/membership/$membershipTypeIdPath")
    }


    fun earnPoint(
        request: EarnPointRequest,
        header: Header,
        documentId: String,
        reqDoc: RequestFieldsSnippet?,
        respDoc: ResponseFieldsSnippet?
    ): Response {

        return jsonRequestSpec(documentId, reqDoc, respDoc)
            .`when`()
            .header(header)
            .body(request)
            .put("/api/v1/membership/point")
    }

    fun deleteMembership(
        deleteMembershipPath: String,
        header: Header,
        documentId: String,
        respDoc: ResponseFieldsSnippet?
    ): Response {

        return jsonRequestSpec(documentId, null, respDoc)
            .`when`()
            .header(header)
            .delete("/api/v1/membership/$deleteMembershipPath")
    }

}