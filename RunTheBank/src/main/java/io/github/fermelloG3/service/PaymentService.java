package io.github.fermelloG3.service;

import io.github.fermelloG3.domain.entity.Account;
import io.github.fermelloG3.domain.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class PaymentService {

    private AccountRepository accountRepository;

    @Autowired
    public PaymentService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void makePayment(String fromAgency, String toAgency, double amount){
        Account fromAccount = getAccountByAgency(fromAgency);
        Account toAccount = getAccountByAgency(toAgency);

        if(fromAccount.getBalance() < amount){
            throw new IllegalArgumentException("Insufficient funds");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    private Account getAccountByAgency(String agency){
        return accountRepository.findByAgency(agency)
                .orElseThrow(() -> new IllegalArgumentException("Account not found for agency: " + agency));
    }


}
