package com.wani.kakao

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication
class KakaoApplication

fun main(args: Array<String>) {
    runApplication<KakaoApplication>(*args)
}
