package io.github.fermelloG3.service;

import io.github.fermelloG3.domain.entity.Customer;
import io.github.fermelloG3.domain.repository.CustomerRepository;
import io.github.fermelloG3.rest.dto.CustomerDTO;
import io.github.fermelloG3.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCustomers() {
        when(customerRepository.findAll()).thenReturn(new ArrayList<>());
        List<Customer> hobbits = customerService.getAllCustomers();
        assertNotNull(hobbits);
        assertTrue(hobbits.isEmpty());
    }

    @Test
    void testFindCustomersById() {
        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(new Customer()));
        Optional<Customer> aragorn = customerService.findCustomersById(customerId);
        assertTrue(aragorn.isPresent());
    }

    @Test
    void testCreateCustomer() {
        CustomerDTO customerDTO = new CustomerDTO("Frodo Baggins", "12345", "Shire", "ringBearer");
        when(customerRepository.existsByDocument(customerDTO.getDocument())).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer());
        Customer createdCustomer = customerService.createCustomer(customerDTO);
        assertNotNull(createdCustomer);
    }

    @Test
    void testCreateCustomerWithExistingDocument() {
        CustomerDTO customerDTO = new CustomerDTO("Aragorn", "12345", "Rivendell", "swordMaster");
        when(customerRepository.existsByDocument(customerDTO.getDocument())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> customerService.createCustomer(customerDTO));
    }

    @Test
    void testUpdateCustomer() {
        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(new Customer()));
        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer());
        CustomerDTO customerDTO = new CustomerDTO("Legolas", "98765", "Mirkwood", "bowMaster");
        Customer updatedCustomer = customerService.updateCustomer(customerId, customerDTO);
        assertNotNull(updatedCustomer);
        assertEquals(customerDTO.getName(), updatedCustomer.getName());
        assertEquals(customerDTO.getAdress(), updatedCustomer.getAdress());
        assertEquals(customerDTO.getPassword(), updatedCustomer.getPassword());
    }

    @Test
    void testUpdateCustomerNotFound() {
        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> customerService.updateCustomer(customerId, new CustomerDTO()));
    }

    @Test
    void testDeleteCustomer() {
        Long customerId = 1L;
        when(customerRepository.existsById(customerId)).thenReturn(true);
        assertDoesNotThrow(() -> customerService.deleteCustomer(customerId));
        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    void testDeleteCustomerNotFound() {
        Long customerId = 1L;
        when(customerRepository.existsById(customerId)).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> customerService.deleteCustomer(customerId));
    }

    @Test
    void testFindCustomerByid() {
        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(new Customer()));
        Optional<Customer> frodo = customerService.findCustomerByid(customerId);
        assertTrue(frodo.isPresent());
    }
}
