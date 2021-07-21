package com.wani.kakao.membership.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class MembershipStatus(
    private val key: String
) {
    TRUE("Y"), FALSE("N");

    @JsonValue
    @Suppress("unused")
    fun toValue(): String = key


    companion object {

        @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
        @JvmStatic
        fun byKey(key: String?) = values().firstOrNull() { it.key == key } ?: FALSE
    }
}