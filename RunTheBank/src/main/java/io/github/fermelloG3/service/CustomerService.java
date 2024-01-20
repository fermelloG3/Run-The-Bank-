package io.github.fermelloG3.service;

import io.github.fermelloG3.domain.entity.Customer;
import io.github.fermelloG3.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer){
        if(customerRepository.existsByDocument(customer.getDocument())){
            throw new IllegalArgumentException("Customer with the same document already exists");
        }
        return customerRepository.save(customer);
    }

    public Optional<Customer> findCustomerByid(Long customerId){
        return customerRepository.findById(customerId);
    }
}
