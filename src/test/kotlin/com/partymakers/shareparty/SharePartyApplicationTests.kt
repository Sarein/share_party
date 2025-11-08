package com.partymakers.shareparty

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("ci")
class SharePartyApplicationTests {

    @Test
    fun contextLoads() {
        // Проверяем, что Spring контекст загружается успешно
    }

    @Test
    fun applicationStarts() {
        // Проверяем, что приложение запускается
        assert(true) // Простая проверка, что тест выполняется
    }
} 