package com.wani.kakao.membership.application

import com.wani.kakao.membership.dto.request.EarnPointRequest

interface UpdateMembershipService {

    fun earnPoint(userId: String, earnPointRequest: EarnPointRequest)

}
