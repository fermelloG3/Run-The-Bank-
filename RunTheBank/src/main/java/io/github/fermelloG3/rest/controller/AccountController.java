package io.github.fermelloG3.rest.controller;

import io.github.fermelloG3.domain.entity.Account;
import io.github.fermelloG3.rest.dto.CustomerDTO;
import io.github.fermelloG3.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Listar",description = "Metodo que retorna todas as contas",tags = "Accounts")
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts(){
        List<Account> accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts,HttpStatus.OK);
    }

    @Operation(summary = "Busca conta por Id",description = "Metodo que retorna uma conta por id",tags = "Accounts")
    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id){
        Optional<Account> account = accountService.finAccountsById(id);
        return account.map(value-> new ResponseEntity<>(value,HttpStatus.OK))
                .orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Criar conta", description = "Metodo para criar conta", tags = "Accounts")
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody CustomerDTO customerDTO){
        try {
            Account newAccount = accountService.createAccount(customerDTO);
            return new ResponseEntity<>(newAccount,HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @Operation(summary = "Atualizar", description = "Metodo para atualizar conta", tags = "Accounts")
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

    @Operation(summary = "Deletar", description = "Metodo para deletar conta", tags = "Accounts")
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
