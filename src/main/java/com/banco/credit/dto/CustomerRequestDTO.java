package com.banco.credit.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;

// Usamos 'record' de Java. Es inmutable por defecto, ideal para DTOs.
@Builder
public record CustomerRequestDTO(

        @NotBlank(message = "El número de documento es obligatorio")
        @Size(min = 8, max = 20, message = "El documento debe tener entre 8 y 20 caracteres")
        String documentNumber,

        @NotBlank(message = "El nombre es obligatorio")
        String firstName,

        @NotBlank(message = "El apellido es obligatorio")
        String lastName,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "Formato de email inválido")
        String email,

        @NotNull(message = "La fecha de nacimiento es obligatoria")
        @Past(message = "La fecha de nacimiento debe ser en el pasado")
        LocalDate birthDate,

        @NotNull(message = "Los ingresos mensuales son obligatorios")
        @Positive(message = "Los ingresos deben ser mayores a 0")
        Double monthlyIncome
) {}