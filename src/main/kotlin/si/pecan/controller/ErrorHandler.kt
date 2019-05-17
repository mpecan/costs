package si.pecan.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.validation.ValidationException


@RestControllerAdvice
class ErrorHandler {

    @ExceptionHandler(ValidationException::class)
    fun handleBindingErrors(ex: Exception): ResponseEntity<*> {
        return ResponseEntity(ValidationError(ex.message), HttpStatus.BAD_REQUEST)
    }
}

data class ValidationError(val error: String?)