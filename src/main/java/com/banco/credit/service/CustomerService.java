package com.banco.credit.service;

import com.banco.credit.dto.CustomerRequestDTO;
import com.banco.credit.dto.CustomerResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomerService {

    CustomerResponseDTO registerCustomer(CustomerRequestDTO requestDTO);

    List<CustomerResponseDTO> getAllCustomers();

    CustomerResponseDTO getCustomerById(Long id);

    CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO requestDTO);

    void deleteCustomer(Long id);

    CustomerResponseDTO getCustomerByDocument(String documentNumber);
}