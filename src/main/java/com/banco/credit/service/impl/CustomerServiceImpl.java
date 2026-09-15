package com.banco.credit.service.impl;

import com.banco.credit.dto.CustomerRequestDTO;
import com.banco.credit.dto.CustomerResponseDTO;
import com.banco.credit.exception.ResourceNotFoundException;
import com.banco.credit.mapper.CustomerMapper;
import com.banco.credit.model.CustomerEntity;
import com.banco.credit.repository.CustomerRepository;
import com.banco.credit.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    @Transactional
    public CustomerResponseDTO registerCustomer(CustomerRequestDTO requestDTO) {
        if (customerRepository.existsByDocumentNumber(requestDTO.documentNumber())) {
            throw new IllegalArgumentException("El cliente con el documento " + requestDTO.documentNumber() + " ya existe.");
        }
        if (customerRepository.existsByEmail(requestDTO.email())) {
            throw new IllegalArgumentException("El email " + requestDTO.email() + " ya está registrado.");
        }

        CustomerEntity customerEntity = customerMapper.toEntity(requestDTO);
        CustomerEntity savedCustomer = customerRepository.save(customerEntity);
        log.info("Cliente registrado con ID: {}", savedCustomer.getId());

        return customerMapper.toResponseDTO(savedCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toResponseDTO)
                .toList(); // Funcional: Stream mapeado a lista inmutable
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDTO getCustomerById(Long id) {
        // Uso estricto de Optional: Extraemos la entidad o lanzamos error en una sola línea
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));

        return customerMapper.toResponseDTO(customer);
    }

    @Override
    @Transactional
    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO requestDTO) {
        // 1. Buscamos (Optional)
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));

        // 2. Validamos que el nuevo email/documento no pertenezca a OTRO cliente
        if (!customer.getDocumentNumber().equals(requestDTO.documentNumber()) &&
                customerRepository.existsByDocumentNumber(requestDTO.documentNumber())) {
            throw new IllegalArgumentException("El documento ya está en uso por otro cliente.");
        }
        if (!customer.getEmail().equals(requestDTO.email()) &&
                customerRepository.existsByEmail(requestDTO.email())) {
            throw new IllegalArgumentException("El email ya está en uso por otro cliente.");
        }

        // 3. Actualizamos usando el mapper
        customerMapper.updateEntityFromRequest(requestDTO, customer);
        CustomerEntity updatedCustomer = customerRepository.save(customer);
        log.info("Cliente actualizado con ID: {}", updatedCustomer.getId());

        return customerMapper.toResponseDTO(updatedCustomer);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer", "id", id);
        }
        customerRepository.deleteById(id);
        log.info("Cliente eliminado con ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDTO getCustomerByDocument(String documentNumber) {
        return customerRepository.findByDocumentNumber(documentNumber)
                .map(customerMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "documentNumber", documentNumber));

    }
}