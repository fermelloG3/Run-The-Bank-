package io.github.fermelloG3.service;

import io.github.fermelloG3.domain.entity.Account;
import io.github.fermelloG3.domain.entity.Customer;
import io.github.fermelloG3.domain.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Customer customer){
        Account newAccount = new Account();
        newAccount.setCustomer(customer);
        newAccount.setBalance(0.0);
        newAccount.setActive(true);

        return accountRepository.save(newAccount);
    }
}
