package io.github.fermelloG3.service;

import io.github.fermelloG3.domain.entity.Account;
import io.github.fermelloG3.domain.repository.AccountRepository;
import io.github.fermelloG3.rest.dto.CustomerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAccounts() {

        when(accountRepository.findAll()).thenReturn(Arrays.asList(new Account(), new Account()));


        List<Account> accounts = accountService.getAllAccounts();


        assertEquals(2, accounts.size());
    }

    @Test
    public void testFindAccountsById() {

        Long accountId = 1L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(new Account()));


        Optional<Account> account = accountService.finAccountsById(accountId);


        assertTrue(account.isPresent());
    }

    @Test
    void testCreateAccount() {

        CustomerDTO customerDTO = new CustomerDTO("Frodo Baggins", "12345", "The Shire", "ringbearer");
        when(accountRepository.save(any(Account.class))).thenReturn(new Account());


        Account createdAccount = accountService.createAccount(customerDTO);


        assertNotNull(createdAccount);
        assertEquals(BigDecimal.ZERO, createdAccount.getBalance());
        assertTrue(createdAccount.isActive());
        assertNotNull(createdAccount.getCustomer());
        assertEquals("Frodo Baggins", createdAccount.getCustomer().getName());
        assertEquals("12345", createdAccount.getCustomer().getDocument());
        assertEquals("The Shire", createdAccount.getCustomer().getAdress());
        assertEquals("ringbearer", createdAccount.getCustomer().getPassword());
    }


    @Test
    public void testUpdateAccount() {

        Long accountId = 1L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(new Account()));
        when(accountRepository.save(any(Account.class))).thenReturn(new Account());


        CustomerDTO customerDTO = new CustomerDTO("John Doe", "newPassword");
        Account updatedAccount = accountService.updateAccount(accountId, customerDTO);


        assertNotNull(updatedAccount);
        assertEquals(customerDTO.getName(), updatedAccount.getCustomer().getName());
        assertEquals(customerDTO.getPassword(), updatedAccount.getCustomer().getPassword());
        assertEquals(customerDTO.getAdress(), updatedAccount.getCustomer().getAdress());
    }

    @Test
    public void testDeleteAccount() {

        Long accountId = 1L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(new Account()));


        assertDoesNotThrow(() -> accountService.deleteAccount(accountId));


        verify(accountRepository, times(1)).delete(any(Account.class));
    }
}
