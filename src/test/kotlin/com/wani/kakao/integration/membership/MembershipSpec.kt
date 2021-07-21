package com.wani.kakao.integration.membership

import com.wani.kakao.common.dto.response.ErrorResponse
import com.wani.kakao.integration.MemberShipTestBase
import com.wani.kakao.integration.membership.MemberShipConst.MEMBER_SHIP_ID_CJ
import com.wani.kakao.integration.membership.response.CreateMembershipTestResponse
import com.wani.kakao.integration.membership.response.FindMembershipTestResponse
import com.wani.kakao.membership.dto.request.CreateMembershipRequest
import com.wani.kakao.membership.dto.request.EarnPointRequest
import com.wani.kakao.membership.model.MembershipStatus
import io.restassured.http.Header
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.provider.Arguments
import org.springframework.http.HttpStatus
import java.util.stream.Stream

class MembershipSpec : MemberShipTestBase() {

    @Test
    fun `멤버십을 만들때 MembershipType이 Null일경우 익셉션이 발생한다`() {

        val header = Header("X-USER-ID", MemberShipConst.MEMBER_ID)

        val request = mutableMapOf<String, String>()
        request["membershipId"] = ""
        request["membershipName"] = "test"
        request["point"] = "0"
        val response = createMembershipRequestMap(
            request,
            header,
            "createMembershipRequestMembershipTypeNullException",
            null,
            null
        )
        val expectResponse = expectError(
            response,
            HttpStatus.BAD_REQUEST,
            ErrorResponse::class.java
        )
        assertThat(expectResponse.status).isEqualTo(400)
    }

    @Test
    fun `멤버십을 만들때 멤버십 타입이 맞지 않으면 익셉션이 발생한다`() {
        val header = Header("X-USER-ID", MemberShipConst.MEMBER_ID)

        val request = mutableMapOf<String, String>()
        request["membershipId"] = "test"
        request["membershipName"] = "test1"
        request["point"] = "0"
        val response = createMembershipRequestMap(
            request,
            header,
            "createMembershipRequestMembershipTypeNotMatchException",
            null,
            null
        )
        val expectResponse = expectError(
            response,
            HttpStatus.BAD_REQUEST,
            ErrorResponse::class.java
        )
        assertThat(expectResponse.status).isEqualTo(400)
    }

    @Test
    fun `멤버십을 만들때 멤버십 포인트는 0 이하일경우 익셉션이 발생한다`() {

        val header = Header("X-USER-ID", MemberShipConst.MEMBER_ID)

        val createMembershipRequest = CreateMembershipRequest(
            MEMBER_SHIP_ID_CJ,
            MemberShipConst.MEMBERSHIP_NAME_CJ,
            -1
        )

        val response = createMembership(
            createMembershipRequest,
            header,
            "createMembershipPointMinException",
            null,
            null
        )

        val expectResponse = expectError(
            response,
            HttpStatus.BAD_REQUEST,
            ErrorResponse::class.java
        )
        assertThat(expectResponse.status).isEqualTo(400)
    }

    @Test
    fun `멤버십을 만들고 등록되어있지 않은 멤버십 타입을 조회하면 실패한다`() {

        val header = Header("X-USER-ID", MemberShipConst.MEMBER_ID)

        val createMembershipRequest = CreateMembershipRequest(
            MEMBER_SHIP_ID_CJ,
            MemberShipConst.MEMBERSHIP_NAME_CJ,
            MemberShipConst.POINT
        )
        createMembership(
            createMembershipRequest,
            header,
            "createMembershipNoSuchMemberException",
            null,
            null
        )

        val findResponse = findOneMembership(
            "not_registed_membership",
            header,
            "findOneMembershipNoSuchMemberException",
            null
        )

        val expectResponse = expectError(
            findResponse,
            HttpStatus.NOT_FOUND,
            ErrorResponse::class.java
        )
        assertThat(expectResponse.status).isEqualTo(404)
    }


    @Test
    fun `멤버십을 만들고 포인트를 적립할때 0이하의 포인트는 적립할수 없다`() {

        val header = Header("X-USER-ID", MemberShipConst.MEMBER_ID)

        val createMembershipRequest = CreateMembershipRequest(
            MEMBER_SHIP_ID_CJ,
            MemberShipConst.MEMBERSHIP_NAME_CJ,
            MemberShipConst.POINT
        )
        createMembership(
            createMembershipRequest,
            header,
            "createMembershipMinPointException",
            null,
            null
        )
        val earnPointRequest = EarnPointRequest(
            membershipId = MEMBER_SHIP_ID_CJ,
            amount = -1
        )
        val findResponse = earnPoint(
            earnPointRequest,
            header,
            "updateMembershipMinPointException",
            null,
            null
        )

        val expectResponse = expectError(
            findResponse,
            HttpStatus.BAD_REQUEST,
            ErrorResponse::class.java
        )
        assertThat(expectResponse.status).isEqualTo(400)
    }



    @Test
    fun `멤버십 통합 테스트 - 멤버십을 만들고 리스트를 조회하고 포인트를 수정하고 삭제할수 있다`() {
        val header = Header("X-USER-ID", MemberShipConst.MEMBER_ID)

        createMembershipStep(header)

        findMembershipsStep(header)

        findOneMembershipStep(header)

        earnPointStep(header)

        deleteMembershipStep(header)
    }


    private fun createMembershipStep(header: Header) {
        val createMembershipRequest = CreateMembershipRequest(
            MEMBER_SHIP_ID_CJ,
            MemberShipConst.MEMBERSHIP_NAME_CJ,
            MemberShipConst.POINT
        )

        val createMembershipResponse = createMembership(
            createMembershipRequest,
            header,
            "createMembership",
            MembershipRequestFieldsDoc.createMembershipRequestFieldsDoc(),
            MembershipResponseFieldsDoc.createMembershipResponseFieldDoc()
        )

        val expectedCreateMembershipResponse =
            expectResponse(
                createMembershipResponse,
                HttpStatus.OK,
                CreateMembershipTestResponse::class.java
            )

        assertThat(expectedCreateMembershipResponse.seq)
            .isNotNull
        assertThat(expectedCreateMembershipResponse.membershipName)
            .isEqualTo(MemberShipConst.MEMBERSHIP_NAME_CJ)
        assertThat(expectedCreateMembershipResponse.membershipId)
            .isEqualTo(MemberShipConst.MEMBERSHIP_ID_CJ)
        assertThat(expectedCreateMembershipResponse.startDate)
            .isNotNull
        assertThat(expectedCreateMembershipResponse.point)
            .isEqualTo(MemberShipConst.POINT)
        assertThat(expectedCreateMembershipResponse.membershipStatus)
            .isEqualTo(MembershipStatus.TRUE.toValue())
    }

    private fun findOneMembershipStep(header: Header) {
        val findMembershipResponse = findOneMembership(
            "cj",
            header,
            "findOneMembership",
            MembershipResponseFieldsDoc.findOneMembershipResponseFieldDoc()
        )

        val expectedFindMembershipResponse =
            expectResponse(
                findMembershipResponse,
                HttpStatus.OK,
                FindMembershipTestResponse::class.java
            )
        assertThat(expectedFindMembershipResponse.seq)
            .isNotNull
        assertThat(expectedFindMembershipResponse.membershipName)
            .isEqualTo(MemberShipConst.MEMBERSHIP_NAME_CJ)
        assertThat(expectedFindMembershipResponse.userId)
            .isEqualTo(MemberShipConst.MEMBER_ID)
        assertThat(expectedFindMembershipResponse.membershipId)
            .isEqualTo(MemberShipConst.MEMBERSHIP_ID_CJ)
        assertThat(expectedFindMembershipResponse.startDate)
            .isNotNull
        assertThat(expectedFindMembershipResponse.point)
            .isEqualTo(MemberShipConst.POINT)
        assertThat(expectedFindMembershipResponse.membershipStatus)
            .isEqualTo(MembershipStatus.TRUE.toValue())
    }

    private fun findMembershipsStep(header: Header) {
        val findMembershipResponse = findMemberships(
            header,
            "findMemberships",
            MembershipResponseFieldsDoc.findMembershipsResponseFieldDoc()
        )

        val expectedFindMembershipResponse =
            expectResponse(
                findMembershipResponse,
                HttpStatus.OK,
                Array::class.java
            )

        assertThat(expectedFindMembershipResponse.size)
            .isEqualTo(1)
    }

    private fun earnPointStep(header: Header) {
        val earnPointRequest = EarnPointRequest(
            membershipId = MEMBER_SHIP_ID_CJ,
            amount = 1000
        )

        val earnPointResponse = earnPoint(
            earnPointRequest,
            header,
            "earnPoint",
            MembershipRequestFieldsDoc.earnPointRequestFieldsDoc(),
            MembershipResponseFieldsDoc.earnPointResponseFieldDoc()
        )
        val expectedEarnPointResponse =
            expectResponse(
                earnPointResponse,
                HttpStatus.OK,
                Boolean::class.java
            )
        assertThat(expectedEarnPointResponse)
            .isTrue
    }


    private fun deleteMembershipStep(header: Header) {
        val deleteMembershipResponse = deleteMembership(
            MEMBER_SHIP_ID_CJ.name,
            header,
            "deleteMembership",
            MembershipResponseFieldsDoc.deleteMembershipResponseFieldDoc()
        )
        val expectedDeleteResponse =
            expectResponse(
                deleteMembershipResponse,
                HttpStatus.OK,
                Boolean::class.java
            )

        assertThat(expectedDeleteResponse)
            .isTrue
    }

    companion object {

        @JvmStatic
        @Suppress("unused")
        fun paramsMembershipTypeId(): Stream<Arguments> =
            Stream.of(
                Arguments.of(

                )
            )
    }

}