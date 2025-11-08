package com.partymakers.shareparty.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(
    info = Info(
        title = "Share Party API",
        version = "v1",
        description = "API для управления вечеринками и расходами",
        contact = Contact(
            name = "PartyMakers Team",
            email = "REDACTED__N5__"
        ),
        license = License(
            name = "MIT License",
            url = "https://opensource.org/licenses/MIT"
        )
    ),
    servers = [
        Server(url = "/", description = "Локальный сервер"),
        Server(url = "http://localhost:8080", description = "Локальный сервер с портом")
    ]
)
class OpenApiConfig {
}