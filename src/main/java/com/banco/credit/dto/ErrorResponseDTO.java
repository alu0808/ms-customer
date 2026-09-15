package com.banco.credit.dto;

import java.time.LocalDateTime;
import java.util.List;

// Un record inmutable que estandariza la estructura de error
public record ErrorResponseDTO(
        String code,            // Código corporativo, ej: "ERR-VALIDATION-400"
        String message,         // Mensaje amigable para el frontend
        List<String> details,   // Lista de errores específicos (ej: "email: Formato inválido")
        LocalDateTime timestamp // Cuándo ocurrió el error (vital para la trazabilidad en logs)
) {}