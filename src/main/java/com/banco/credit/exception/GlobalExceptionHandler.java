package com.banco.credit.exception;

import com.banco.credit.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Atrapa los errores de @Valid (ej. Email con formato incorrecto) -> 400 Bad Request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "ERR-VALIDATION-400",
                "Error en la validación de los datos enviados.",
                errors,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 2. Atrapa errores cuando no encontramos un registro (Optional vacío) -> 404 Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "ERR-NOT-FOUND-404",
                ex.getMessage(),
                List.of("El recurso solicitado no existe en la base de datos."),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // 3. Atrapa errores lógicos del negocio, como intentar crear un usuario con DNI repetido -> 409 Conflict
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "ERR-BUSINESS-409",
                ex.getMessage(),
                List.of("Violación de regla de negocio."),
                LocalDateTime.now()
        );

        // Usamos HttpStatus.CONFLICT porque los datos chocan con los ya existentes en BD
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    // 4. Atrapa cualquier otro error catastrófico (NullPointer, Base de Datos caída) -> 500 Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleAllUncaughtExceptions(Exception ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "ERR-SYS-500",
                "Ha ocurrido un error interno en el servidor. Por favor, contacte a soporte técnico.",
                List.of(ex.getMessage()),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}