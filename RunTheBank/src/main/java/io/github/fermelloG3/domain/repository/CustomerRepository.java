package io.github.fermelloG3.domain.repository;

import io.github.fermelloG3.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Optional<Customer> findByDocument(String document);

    boolean existsByDocument(String document);

}
