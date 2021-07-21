package com.wani.kakao.unit.membership

import com.wani.kakao.membership.entity.Point
import com.wani.kakao.membership.entity.PointRatio
import com.wani.kakao.membership.exception.PointMinException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class PointTest {

    @Test
    fun `포인트는 최소 0 이어야 합니다`() {
        Assertions.assertThatThrownBy {
            Point.wons(-1)
        }.isInstanceOf(PointMinException::class.java)
    }

    @ParameterizedTest
    @MethodSource("paramsPlusPoint")
    fun `포인트 플러스 테스트`(prevPoint: Point, nextPoint: Point, expected: Point) {

        Assertions.assertThat(prevPoint.plus(nextPoint)).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("paramsTimesPoint")
    fun `포인트 곱하기 테스트`(prevPoint: Point, ratio: Double, expected: Point) {

        Assertions.assertThat(prevPoint.times(ratio)).isEqualTo(expected)
    }

    companion object {
        @JvmStatic
        @Suppress("unused")
        fun paramsPlusPoint(): Stream<Arguments> =
            Stream.of(
                Arguments.of(
                    Point.wons(1000), Point.wons(1000), Point.wons(2000)
                ),
                Arguments.of(
                    Point.wons(1000), Point.wons(2000), Point.wons(3000)
                ),
                Arguments.of(
                    Point.wons(14000), Point.wons(8000), Point.wons(22000)
                ),
            )

        @JvmStatic
        @Suppress("unused")
        fun paramsTimesPoint(): Stream<Arguments> =
            Stream.of(
                Arguments.of(
                    Point.wons(1000), 2.0, Point.wons(2000)
                ),
                Arguments.of(
                    Point.wons(1000), 3.0, Point.wons(3000)
                ),
                Arguments.of(
                    Point.wons(4000), 5.0, Point.wons(20000)
                ),
            )
    }
}