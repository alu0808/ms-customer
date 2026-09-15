package com.banco.credit.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

//@Data Genera getters, setters, toString, equals y hashCode automáticamente
@Getter @Setter
@Builder // Nos permite usar el patrón Builder para instanciar clientes de forma limpia
@AllArgsConstructor // Genera un constructor con todos los campos
@NoArgsConstructor // Genera un constructor vacío (requerido por JPA/Hibernate)
@Entity // Le dice a Spring: "Esta clase es una tabla de base de datos"
@Table(name = "customers") // El nombre físico de la tabla en SQL Server
public class CustomerEntity {

    @Id // Llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    private Long id;

    @Column(name = "document_number", nullable = false, unique = true, length = 20)
    private String documentNumber; // DNI o Pasaporte

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "monthly_income", nullable = false)
    private Double monthlyIncome; // Ingresos mensuales, vital para el motor de evaluación crediticia
}