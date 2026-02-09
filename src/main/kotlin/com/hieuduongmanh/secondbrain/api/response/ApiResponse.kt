package com.hieuduongmanh.secondbrain.api.response

import java.time.Instant

data class ApiResponse<T>(
    val timestamp: Instant = Instant.now(),
    val status: Int,
    val message: String,
    val data: T?,
    val error: ErrorDetails? = null,
)

data class ErrorDetails(
    val code: String,
    val description: String,
)
