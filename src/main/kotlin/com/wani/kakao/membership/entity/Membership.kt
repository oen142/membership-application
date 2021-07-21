package com.wani.kakao.membership.entity

import com.wani.kakao.common.entity.BaseEntity
import com.wani.kakao.membership.entity.PointRatio.Companion.DEFAULT_POINT_RATIO
import com.wani.kakao.membership.exception.AmountIllegalArgumentException
import com.wani.kakao.membership.exception.ExistDeletedMembershipException
import com.wani.kakao.membership.model.MembershipStatus
import com.wani.kakao.membership.model.MembershipType
import com.wani.kakao.membership.model.MembershipTypeId
import javax.persistence.*

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint
        (columnNames = arrayOf("user_id", "membership_id"))]
)
class Membership(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "membership_idx")
    var id: Long?,

    @Column(name = "user_id", nullable = false)
    var userId: String,

    @Embedded
    var membershipType: MembershipType,

    @Enumerated(EnumType.STRING)
    @Column(name = "membership_status", nullable = false)
    var membershipStatus: MembershipStatus? = MembershipStatus.TRUE,

    @Embedded
    @Column(name = "point", nullable = false)
    var point: Point = Point.ZERO
) : BaseEntity() {


    fun deleteMembership() {
        validMembershipStatus()
        this.membershipStatus = MembershipStatus.FALSE
    }

    private fun validMembershipStatus() {
        if (this.membershipStatus == MembershipStatus.FALSE) {
            throw ExistDeletedMembershipException("이미 삭제된 멤버십입니다.")
        }
    }

    fun earnPoint(amount: Int) {
        validAmount(amount)

        this.point = membershipType.pointRatio
            .of(Point.wons(amount))
            .plus(this.point)
    }

    private fun validAmount(amount: Int) {
        if (amount <= 0) {
            throw AmountIllegalArgumentException("결제금액 0원 이하는 적립할수 없습니다.")
        }
    }

}