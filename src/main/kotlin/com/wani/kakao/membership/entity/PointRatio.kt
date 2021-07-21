package com.wani.kakao.membership.entity

import com.wani.kakao.membership.exception.PointMinException
import com.wani.kakao.membership.exception.PointRatioMinException
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class PointRatio(
    @Column(name = "rate", nullable = false)
    val rate: Double
) {
    init {
        require(validMinZero(rate)) { throw PointRatioMinException("비율은 최소 0입니다.") }
    }

    private fun validMinZero(rate: Double): Boolean {
        if (rate < 0) {
            return false
        }
        return true
    }

    fun of(price: Point): Point =
        price.times(rate)


    companion object {
        val DEFAULT_POINT_RATIO = PointRatio(0.1)
    }
}