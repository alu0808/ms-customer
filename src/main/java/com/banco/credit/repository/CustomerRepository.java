package com.banco.credit.repository;

import com.banco.credit.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    // Con solo nombrar bien el mét0do, Spring genera el "SELECT * WHERE document_number = ?"
    boolean existsByDocumentNumber(String documentNumber);

    boolean existsByEmail(String email);

    Optional<CustomerEntity> findByDocumentNumber(String documentNumber);
}