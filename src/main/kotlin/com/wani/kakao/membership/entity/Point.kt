package com.wani.kakao.membership.entity

import com.wani.kakao.membership.exception.PointMinException
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class Point(
    @Column(name = "point", nullable = false)
    val point: BigDecimal
) {
    init {
        require(validMinZero(point)) { throw PointMinException("포인트는 0이상만 사용 가능합니다.") }
    }

    private fun validMinZero(point: BigDecimal): Boolean {
        if (point < BigDecimal.valueOf(0)) {
            return false
        }
        return true
    }

    fun plus(amount: Point): Point =
        Point(this.point.add(amount.point))

    fun times(percent: Double) =
        Point(this.point.multiply(BigDecimal.valueOf(percent)))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point

        if (point.toDouble() != other.point.toDouble()) return false

        return true
    }

    override fun hashCode(): Int {
        return point.hashCode()
    }

    fun toInt(): Int =
        point.toInt()

    companion object {
        val ZERO: Point = wons(0)

        fun wons(amount: Long): Point =
            Point(BigDecimal.valueOf(amount))

        fun wons(amount: Int): Point =
            wons(amount.toLong())
    }


}