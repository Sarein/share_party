package ru.rustore.commons.dto

import java.time.ZoneOffset
import java.time.ZonedDateTime

data class DefaultResponseDto<T>(
    val code: String? = null,
    val message: String? = null,
    val body: T? = null,
    val timestamp: ZonedDateTime? = null
) {
    companion object {
        @JvmStatic
        fun <T> okResponse(body: T, message: String? = null): DefaultResponseDto<T> {
            return DefaultResponseDto(
                code = "OK",
                body = body,
                timestamp = ZonedDateTime.now(ZoneOffset.UTC),
                message = message
            )
        }

        @JvmStatic
        fun <T> okResponse(body: T): DefaultResponseDto<T> {
            return DefaultResponseDto(
                code = "OK",
                body = body,
                timestamp = ZonedDateTime.now(ZoneOffset.UTC),
                message = null
            )
        }

        @JvmStatic
        fun <T> errorResponse(body: T, message: String? = null): DefaultResponseDto<T> {
            return DefaultResponseDto(
                code = "ERROR",
                body = body,
                timestamp = ZonedDateTime.now(ZoneOffset.UTC),
                message = message
            )
        }

        @JvmStatic
        fun <T> errorResponse(message: String? = null): DefaultResponseDto<T> {
            return DefaultResponseDto(
                code = "ERROR",
                body = null,
                timestamp = ZonedDateTime.now(ZoneOffset.UTC),
                message = message
            )
        }

        @JvmStatic
        fun <T> codedResponse(code: String? = null, body: T, message: String? = null): DefaultResponseDto<T> {
            return DefaultResponseDto(
                code = code,
                body = body,
                timestamp = ZonedDateTime.now(ZoneOffset.UTC),
                message = message
            )
        }

        @JvmStatic
        fun <T> codedResponse(code: String? = null, message: String? = null): DefaultResponseDto<T> {
            return DefaultResponseDto(
                code = code,
                body = null,
                timestamp = ZonedDateTime.now(ZoneOffset.UTC),
                message = message
            )
        }
    }
}
