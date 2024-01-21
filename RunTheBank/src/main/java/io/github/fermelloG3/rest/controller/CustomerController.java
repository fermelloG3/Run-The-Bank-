package io.github.fermelloG3.rest.controller;

import io.github.fermelloG3.rest.dto.CustomerDTO;
import io.github.fermelloG3.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createCustomer(@ResponseBody CustomerDTO customerDTO){
        try {
            return customerService.createCustomer(customerDTO);
        } catch (DuplicateKeyException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Customer already exists", e);
        }
    }
}
