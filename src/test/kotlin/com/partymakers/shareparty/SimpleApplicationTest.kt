package com.partymakers.shareparty

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = [
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.show-sql=true"
])
class SimpleApplicationTest {

    @Test
    fun contextLoads() {
        // Проверяем, что Spring контекст загружается успешно
        assert(true)
    }

    @Test
    fun applicationStarts() {
        // Проверяем, что приложение запускается
        assert(true)
    }
} 