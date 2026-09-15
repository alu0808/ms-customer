package com.banco.credit.exception;

// Heredar de RuntimeException nos evita ensuciar las firmas de los métodos con "throws"
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s no encontrado con %s : '%s'", resourceName, fieldName, fieldValue));
    }
}