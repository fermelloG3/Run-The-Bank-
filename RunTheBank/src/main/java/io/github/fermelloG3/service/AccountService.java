package io.github.fermelloG3.service;

import io.github.fermelloG3.domain.entity.Account;
import io.github.fermelloG3.domain.entity.Customer;
import io.github.fermelloG3.domain.repository.AccountRepository;
import io.github.fermelloG3.rest.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(CustomerDTO customerDTO){
        Account newAccount = new Account();
        newAccount.setBalance(0.0);
        newAccount.setActive(true);

        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setDocument(customerDTO.getDocument());

        newAccount.setCustomer(customer);

        return accountRepository.save(newAccount);
    }
}
