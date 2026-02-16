package com.hieuduongmanh.secondbrain.dto.response

import java.time.Instant

data class ApiResponse<T>(
    val timestamp: Instant = Instant.now(),
    val status: Int,
    val message: String,
    val data: T? = null,
    val error: ErrorDetails? = null,
)

data class ErrorDetails(
    val code: String,
    val description: String,
)
