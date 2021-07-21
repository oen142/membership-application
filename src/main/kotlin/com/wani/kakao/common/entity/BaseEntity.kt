package com.wani.kakao.common.entity

import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.MappedSuperclass

@MappedSuperclass
class BaseEntity {

    @CreatedDate
    var startDate: LocalDateTime = LocalDateTime.now()
}
