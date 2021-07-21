package com.wani.kakao.membership.application

interface DeleteMembershipService {

    fun deleteMembership(userId: String, membershipId: String)
}