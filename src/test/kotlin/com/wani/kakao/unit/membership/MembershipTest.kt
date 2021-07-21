package com.wani.kakao.unit.membership

import com.wani.kakao.membership.entity.Membership
import com.wani.kakao.membership.entity.Point
import com.wani.kakao.membership.entity.PointRatio
import com.wani.kakao.membership.exception.ExistDeletedMembershipException
import com.wani.kakao.membership.exception.PointRatioMinException
import com.wani.kakao.membership.model.MembershipStatus
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class MembershipTest {


    @ParameterizedTest
    @MethodSource("paramsCreated")
    fun `생성_완료`(membership: Membership, expected: Membership) {

        assertThat(membership.id).isEqualTo(expected.id)
        assertThat(membership.membershipType.memberShipId).isEqualTo(expected.membershipType.memberShipId)
        assertThat(membership.membershipType.memberShipName).isEqualTo(expected.membershipType.memberShipName)
        assertThat(membership.membershipType.pointRatio).isEqualTo(expected.membershipType.pointRatio)
        assertThat(membership.membershipStatus).isEqualTo(expected.membershipStatus)
        assertThat(membership.startDate).isNotNull
        assertThat(membership.point.toInt()).isEqualTo(expected.point.toInt())
    }

    @ParameterizedTest
    @MethodSource("paramsPointStack")
    fun `포인트_적립`(membership: Membership, point: Int, expected: Point) {

        membership.earnPoint(point)

        assertThat(membership.point.toInt()).isEqualTo(expected.toInt())
    }


    @Test
    fun `삭제_완료`() {
        val membership = MembershipFixtures.aMembership()

        membership.deleteMembership()

        assertThat(membership.membershipStatus).isEqualTo(MembershipStatus.FALSE)
    }

    @Test
    fun `이미 삭제된 멤버십을 삭제하면 Exception이 발생합니다`() {

        val membership = MembershipFixtures.aMembership()

        membership.deleteMembership()

        Assertions.assertThatThrownBy {
            membership.deleteMembership()
        }.isInstanceOf(ExistDeletedMembershipException::class.java)
    }

    companion object {
        @JvmStatic
        @Suppress("unused")
        fun paramsCreated(): Stream<Arguments> =
            Stream.of(
                Arguments.of(
                    MembershipFixtures.aMembership(), MembershipFixtures.aMembership()
                ),
                Arguments.of(
                    MembershipFixtures.bMembership(), MembershipFixtures.bMembership()
                ),
            )

        @JvmStatic
        @Suppress("unused")
        fun paramsPointStack(): Stream<Arguments> =
            Stream.of(
                Arguments.of(
                    MembershipFixtures.aMembership(), 1000, Point.wons(1100)
                ),
                Arguments.of(
                    MembershipFixtures.bMembership(), 3000, Point.wons(10300)
                ),
            )


    }
}