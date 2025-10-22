package com.partymakers.shareparty

import com.partymakers.shareparty.party.domain.exception.AlreadyExistException
import com.partymakers.shareparty.party.domain.exception.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.rustore.commons.dto.DefaultResponseDto

@RestControllerAdvice
internal class SharePartyExceptionHandler {

    @ExceptionHandler(AlreadyExistException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleAlreadyExistException(ex: AlreadyExistException): DefaultResponseDto<Unit> =
        DefaultResponseDto.Companion.errorResponse("Resource already exists")

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFoundException(ex: NotFoundException): DefaultResponseDto<Unit> =
        DefaultResponseDto.Companion.errorResponse("Resource not found")

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGenericException(ex: Exception): DefaultResponseDto<Unit> =
        DefaultResponseDto.Companion.errorResponse("Internal server error")

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(e: IllegalArgumentException): DefaultResponseDto<Unit> =
        DefaultResponseDto.Companion.errorResponse(message = e.message)
}