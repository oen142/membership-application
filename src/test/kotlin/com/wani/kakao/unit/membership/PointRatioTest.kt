package com.wani.kakao.unit.membership

import com.wani.kakao.membership.entity.Point
import com.wani.kakao.membership.entity.PointRatio
import com.wani.kakao.membership.exception.PointMinException
import com.wani.kakao.membership.exception.PointRatioMinException
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class PointRatioTest {
    @Test
    fun `비율은 최소 0 이상 이어야 합니다`() {
        assertThatThrownBy {
            PointRatio(-1.0)
        }.isInstanceOf(PointRatioMinException::class.java)
    }

    @ParameterizedTest
    @MethodSource("paramsRatioPoint")
    fun `퍼센트_테스트`(pointRatio: PointRatio, point: Point, expected: Point) {
        assertThat(pointRatio.of(point)).isEqualTo(expected)
    }

    companion object {
        @JvmStatic
        @Suppress("unused")
        fun paramsRatioPoint(): Stream<Arguments> =
            Stream.of(
                Arguments.of(
                    PointRatio(0.1), Point.wons(1000), Point.wons(100)
                ),
                Arguments.of(
                    PointRatio(0.01), Point.wons(1000), Point.wons(10)
                ),
                Arguments.of(
                    PointRatio(0.5), Point.wons(1000), Point.wons(500)
                ),
            )


    }
}