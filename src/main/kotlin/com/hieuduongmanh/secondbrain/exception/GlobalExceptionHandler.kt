package com.hieuduongmanh.secondbrain.exception

import com.hieuduongmanh.secondbrain.dto.response.ApiResponse
import com.hieuduongmanh.secondbrain.dto.response.ErrorDetails
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(
        ex: ResourceNotFoundException
    ): ResponseEntity<ApiResponse<Unit>> {
        val errorResponse = ApiResponse<Unit>(
            status = HttpStatus.NOT_FOUND.value(),
            message = "Resource not found",
            data = null,
            error = ErrorDetails(
                code = "NOT_FOUND",
                description = ex.message ?: "The requested resource could not be found."
            )
        )
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(
        ex: MethodArgumentTypeMismatchException
    ): ResponseEntity<ApiResponse<Unit>> {
        val errorResponse = ApiResponse<Unit>(
            status = HttpStatus.BAD_REQUEST.value(),
            message = "Invalid argument type",
            data = null,
            error = ErrorDetails(
                code = "BAD_REQUEST",
                description = "The parameter '${ex.name}' of value '${ex.value}' could not be converted to type '${ex.requiredType?.name}'."
            )
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        ex: MethodArgumentNotValidException
    ): ResponseEntity<ApiResponse<Unit>> {
        val errorResponse = ApiResponse<Unit>(
            status = HttpStatus.BAD_REQUEST.value(),
            message = "Validation failed",
            data = null,
            error = ErrorDetails(
                code = "BAD_REQUEST",
                description = "Validation failed for one or more fields."
            )
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
        ex: IllegalArgumentException
    ): ResponseEntity<ApiResponse<Unit>> {
        val errorResponse = ApiResponse<Unit>(
            status = HttpStatus.BAD_REQUEST.value(),
            message = "Illegal argument",
            data = null,
            error = ErrorDetails(
                code = "BAD_REQUEST",
                description = ex.message ?: "An illegal argument was provided."
            )
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ApiResponse<Unit>> {
        val errorResponse = ApiResponse<Unit>(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "Internal server error",
            data = null,
            error = ErrorDetails(
                code = "INTERNAL_SERVER_ERROR",
                description = ex.message ?: "An unexpected error occurred."
            )
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
