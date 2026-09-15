package com.banco.credit.dto;

import lombok.Builder;
import java.time.LocalDate;

@Builder
public record CustomerResponseDTO(
        Long id,
        String documentNumber,
        String fullName, // Fíjate que aquí unimos nombre y apellido para darle el dato procesado al cliente
        String email,
        Double monthlyIncome
) {}