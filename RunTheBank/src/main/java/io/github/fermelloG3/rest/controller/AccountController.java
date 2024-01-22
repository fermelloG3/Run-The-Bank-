package io.github.fermelloG3.rest.controller;

import io.github.fermelloG3.domain.entity.Account;
import io.github.fermelloG3.rest.dto.CustomerDTO;
import io.github.fermelloG3.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts(){
        List<Account> accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts,HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id){
        Optional<Account> account = accountService.finAccountsById(id);
        return account.map(value-> new ResponseEntity<>(value,HttpStatus.OK))
                .orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody CustomerDTO customerDTO){
        try {
            Account newAccount = accountService.createAccount(customerDTO);
            return new ResponseEntity<>(newAccount,HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long accountId,@RequestBody CustomerDTO customerDTO){
        try {
            Account updatedAccount = accountService.updateAccount(accountId,customerDTO);
            return new ResponseEntity<>(updatedAccount,HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId){
        try {
            accountService.deleteAccount(accountId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
