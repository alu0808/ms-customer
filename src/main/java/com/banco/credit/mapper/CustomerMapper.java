package com.banco.credit.mapper;

import com.banco.credit.dto.CustomerRequestDTO;
import com.banco.credit.dto.CustomerResponseDTO;
import com.banco.credit.model.CustomerEntity;
import org.springframework.stereotype.Component;

/**
 * @Component le dice a Spring que gestione esta clase como un Bean.
 * Su única responsabilidad es transformar objetos (Principio de Responsabilidad Única - SOLID).
 */
@Component
public class CustomerMapper {

    // Convierte el DTO de entrada a la Entidad de Base de Datos
    public CustomerEntity toEntity(CustomerRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }

        return CustomerEntity.builder()
                .documentNumber(requestDTO.documentNumber())
                .firstName(requestDTO.firstName())
                .lastName(requestDTO.lastName())
                .email(requestDTO.email())
                .birthDate(requestDTO.birthDate())
                .monthlyIncome(requestDTO.monthlyIncome())
                .build();
    }

    // Convierte la Entidad de Base de Datos al DTO de salida
    public CustomerResponseDTO toResponseDTO(CustomerEntity entity) {
        if (entity == null) {
            return null;
        }

        return CustomerResponseDTO.builder()
                .id(entity.getId())
                .documentNumber(entity.getDocumentNumber())
                // Procesamos el nombre completo aquí para enviarlo limpio al cliente
                .fullName(entity.getFirstName() + " " + entity.getLastName())
                .email(entity.getEmail())
                .monthlyIncome(entity.getMonthlyIncome())
                .build();
    }

    // Mét0do listo para cuando implementemos el Update (PUT)
    public void updateEntityFromRequest(CustomerRequestDTO requestDTO, CustomerEntity entity) {
        if (requestDTO == null || entity == null) {
            return;
        }

        entity.setDocumentNumber(requestDTO.documentNumber());
        entity.setFirstName(requestDTO.firstName());
        entity.setLastName(requestDTO.lastName());
        entity.setEmail(requestDTO.email());
        entity.setBirthDate(requestDTO.birthDate());
        entity.setMonthlyIncome(requestDTO.monthlyIncome());
    }
}