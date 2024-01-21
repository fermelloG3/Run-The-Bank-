package io.github.fermelloG3.service;

import io.github.fermelloG3.domain.entity.Account;
import io.github.fermelloG3.domain.entity.Customer;
import io.github.fermelloG3.domain.repository.CustomerRepository;
import io.github.fermelloG3.rest.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Optional<Customer> findCustomersById(Long customerId){
        return customerRepository.findById(customerId);
    }

    public Customer createCustomer(CustomerDTO customerDTO){
        if(customerRepository.existsByDocument(customerDTO.getDocument())){
            throw new IllegalArgumentException("Customer with the same document already exists");
        }

        Customer newCustomer = new Customer();
        mapCustomerDtoEntity(customerDTO,newCustomer);
        return customerRepository.save(newCustomer);
    }

    public Customer updateCustomer(Long customerId, CustomerDTO customerDTO){
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));

        mapCustomerDtoEntity(customerDTO, existingCustomer);
        return customerRepository.save(existingCustomer);
    }

    public void deleteCustomer(Long customerId){
        if(!customerRepository.existsById(customerId)){
            throw new NoSuchElementException("Customer not found");
        }
        customerRepository.deleteById(customerId);
    }

    private void mapCustomerDtoEntity(CustomerDTO customerDTO, Customer customer){
        customer.setName(customerDTO.getName());
        customer.setDocument(customerDTO.getDocument());
        customer.setAdress(customerDTO.getAdress());
        customer.setPassword(customerDTO.getPassword());
    }

    public Optional<Customer> findCustomerByid(Long customerId){
        return customerRepository.findById(customerId);
    }
}
