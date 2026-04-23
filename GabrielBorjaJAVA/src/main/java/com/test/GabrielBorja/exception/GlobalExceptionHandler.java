package com.test.GabrielBorja.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.validation.BindException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, WebRequest req) {
        log.warn("Recurso no encontrado: {}", ex.getMessage());
        ApiError err = new ApiError(HttpStatus.NOT_FOUND.value(), "Not Found", ex.getMessage(), req.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class, BindException.class, ConstraintViolationException.class })
    public ResponseEntity<ApiError> handleValidation(Exception ex, WebRequest req) {
        log.warn("Error de validación: {}", ex.getMessage());
        String message = extractValidationMessage(ex);
        ApiError err = new ApiError(HttpStatus.BAD_REQUEST.value(), "Validation Error", message, req.getDescription(false));
        return ResponseEntity.badRequest().body(err);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest req) {
        log.warn("Violación de integridad en BD: {}", ex.getMessage());
        String message = extractDatabaseConstraintMessage(ex);
        ApiError err = new ApiError(HttpStatus.BAD_REQUEST.value(), "Data Integrity Error", message, req.getDescription(false));
        return ResponseEntity.badRequest().body(err);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiError> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, WebRequest req) {
        log.warn("Tipo de contenido no soportado: {}", ex.getMessage());
        ApiError err = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), "Unsupported Media Type", 
                "Use Content-Type: application/json", req.getDescription(false));
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAll(Exception ex, WebRequest req) {
        log.error("Error interno no esperado: ", ex);
        ApiError err = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Error", 
                "Ocurrió un error interno. Verifique que los datos sean válidos.", req.getDescription(false));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }

    /**
     * Extrae mensaje legible de excepciones de validación
     */
    private String extractValidationMessage(Exception ex) {
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException mex = (MethodArgumentNotValidException) ex;
            return mex.getBindingResult().getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .findFirst()
                    .orElse("Error de validación en los datos proporcionados");
        }
        return ex.getMessage();
    }

    /**
     * Extrae mensaje legible de excepciones de base de datos
     */
    private String extractDatabaseConstraintMessage(DataIntegrityViolationException ex) {
        return "Error de integridad en la base de datos. Verifique que todos los datos sean correctos.";
    }
}
