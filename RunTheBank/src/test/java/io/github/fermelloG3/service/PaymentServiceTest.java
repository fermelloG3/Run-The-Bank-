package io.github.fermelloG3.service;

import io.github.fermelloG3.domain.entity.Account;
import io.github.fermelloG3.domain.entity.Payment;
import io.github.fermelloG3.domain.enums.PaymentStatus;
import io.github.fermelloG3.domain.repository.AccountRepository;
import io.github.fermelloG3.domain.repository.PaymentRepository;
import io.github.fermelloG3.rest.dto.PaymentDTO;
import io.github.fermelloG3.service.PaymentService;
import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ProducerTemplate producerTemplate;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPayments() {
        when(paymentRepository.findAll()).thenReturn(new ArrayList<>());
        List<Payment> payments = paymentService.getAllPayments();
        assertNotNull(payments);
        assertTrue(payments.isEmpty());
    }

    @Test
    void testFindPaymentById() {
        Long paymentId = 1L;
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(new Payment()));
        Optional<Payment> payment = paymentService.findPaymentById(paymentId);
        assertTrue(payment.isPresent());
    }

    @Test
    void testMakePayment() {
        PaymentDTO paymentDTO = new PaymentDTO("fromAgency", "toAgency", new BigDecimal("50.00"));
        Account senderAccount = new Account();
        senderAccount.setId(1L);
        senderAccount.setBalance(new BigDecimal("100.00"));

        Account receiverAccount = new Account();
        receiverAccount.setId(2L);
        receiverAccount.setBalance(new BigDecimal("0.00"));

        when(accountRepository.findByAgency("fromAgency")).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findByAgency("toAgency")).thenReturn(Optional.of(receiverAccount));

        Payment newPayment = new Payment();
        newPayment.setAmount(paymentDTO.getAmount());
        newPayment.setSenderAccountId(senderAccount.getId());
        newPayment.setReceiverAccountId(receiverAccount.getId());
        newPayment.setStatus(PaymentStatus.COMPLETED);

        when(paymentRepository.save(any(Payment.class))).thenReturn(newPayment);

        assertDoesNotThrow(() -> paymentService.makePayment(paymentDTO));

        assertEquals(new BigDecimal("50.00"), senderAccount.getBalance());
        assertEquals(new BigDecimal("50.00"), receiverAccount.getBalance());
    }

    @Test
    void testMakePaymentInsufficientFunds() {
        PaymentDTO paymentDTO = new PaymentDTO("fromAgency", "toAgency", new BigDecimal("150.00"));
        Account senderAccount = new Account();
        senderAccount.setBalance(new BigDecimal("100.00"));

        when(accountRepository.findByAgency("fromAgency")).thenReturn(Optional.of(senderAccount));

        assertThrows(IllegalArgumentException.class, () -> paymentService.makePayment(paymentDTO));
        assertEquals(new BigDecimal("100.00"), senderAccount.getBalance());
    }

    @Test
    void testCancelPayment() {
        Long paymentId = 1L;
        Payment deletePayment = new Payment();
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(deletePayment));

        assertDoesNotThrow(() -> paymentService.cancelPayment(paymentId));
        verify(paymentRepository, times(1)).delete(deletePayment);
    }

    @Test
    void testCancelPaymentNotFound() {
        Long paymentId = 1L;
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> paymentService.cancelPayment(paymentId));
        verify(paymentRepository, never()).delete(any(Payment.class));
    }
    }