package com.hieuduongmanh.secondbrain.api.exception

import com.hieuduongmanh.secondbrain.api.response.ApiResponse
import com.hieuduongmanh.secondbrain.api.response.ErrorDetails
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
    ): ResponseEntity<ApiResponse<Nothing>> {
        val errorResponse = ApiResponse(
            status = HttpStatus.NOT_FOUND.value(),
            message = "ResourceNotFoundException",
            data = null,
            error = ErrorDetails(
                code = "NOT_FOUND",
                description = ex.message.toString()
            )
        )
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleMethodArgumentTypeMismatchException (
        ex: MethodArgumentTypeMismatchException
    ): ResponseEntity<ApiResponse<Nothing>> {
        val errorResponse = ApiResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = "MethodArgumentTypeMismatchException",
            data = null,
            error = ErrorDetails(
                code = "BAD_REQUEST",
                description = ex.message.toString()
            )
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        ex: MethodArgumentNotValidException
    ): ResponseEntity<ApiResponse<Nothing>> {
        val errorResponse = ApiResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = "MethodArgumentNotValidException",
            data = null,
            error = ErrorDetails(
                code = "BAD_REQUEST",
                description = ex.message.toString()
            )
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleIllegalArgumentException(
        ex: IllegalArgumentException
    ): ResponseEntity<ApiResponse<Nothing>> {
        val errorResponse = ApiResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = "IllegalArgumentException",
            data = null,
            error = ErrorDetails(
                code = "BAD_REQUEST",
                description = ex.message.toString()
            )
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ApiResponse<Nothing>> {
        val errorResponse = ApiResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "Exception",
            data = null,
            error = ErrorDetails(
                code = "INTERNAL_SERVER_ERROR",
                description = ex.message.toString()
            )
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
