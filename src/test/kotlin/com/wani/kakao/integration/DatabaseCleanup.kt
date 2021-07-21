package com.wani.kakao.integration

import com.google.common.base.CaseFormat
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors
import javax.persistence.Entity
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.metamodel.EntityType

@Service
@ActiveProfiles("test")
class DatabaseCleanup(

    @PersistenceContext
    val entityManager: EntityManager
) : InitializingBean {

    lateinit var tableNames: MutableList<String>

    override fun afterPropertiesSet() {
        tableNames = entityManager.metamodel.entities.stream()
            .filter { e: EntityType<*> ->
                e.javaType.getAnnotation(
                    Entity::class.java
                ) != null
            }
            .map { e: EntityType<*> ->
                CaseFormat.UPPER_CAMEL.to(
                    CaseFormat.LOWER_UNDERSCORE,
                    e.name
                )
            }
            .collect(Collectors.toList())
    }

    @Transactional
    fun execute() {
        entityManager.flush()
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate()

        for (tableName in tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE $tableName").executeUpdate()
            entityManager.createNativeQuery("ALTER TABLE $tableName AUTO_INCREMENT = 1")
                .executeUpdate()
        }

        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate()
    }
}