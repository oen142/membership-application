package com.wani.kakao.integration.membership

import com.wani.kakao.membership.model.MembershipType
import com.wani.kakao.membership.model.MembershipTypeId

object MemberShipConst {

    const val MEMBER_ID = "test01"
    val MEMBER_SHIP_ID_CJ = MembershipTypeId.cj
    val MEMBERSHIP_ID_CJ = MembershipType.from(MembershipTypeId.cj).memberShipId
    val MEMBERSHIP_NAME_CJ = MembershipType.from(MembershipTypeId.cj).memberShipName
    const val POINT = 5210
}