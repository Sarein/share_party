package ru.rustore.commons.dto

data class PageResponseDto<T>(
    val content: Collection<T> = emptyList(),
    val pageNumber: Int = 0,
    val pageSize: Int = 0,
    val totalElements: Int = 0,
    val totalPages: Int = 0
)
