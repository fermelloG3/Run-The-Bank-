package io.github.fermelloG3.service;

import io.github.fermelloG3.domain.entity.Account;
import io.github.fermelloG3.domain.entity.Payment;
import io.github.fermelloG3.domain.enums.PaymentStatus;
import io.github.fermelloG3.domain.repository.AccountRepository;
import io.github.fermelloG3.domain.repository.PaymentRepository;
import io.github.fermelloG3.rest.dto.PaymentDTO;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;
    private final ProducerTemplate producerTemplate;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, AccountRepository accountRepository,
                          ProducerTemplate producerTemplate){
        this.paymentRepository = paymentRepository;
        this.accountRepository = accountRepository;
        this.producerTemplate = producerTemplate;
    }


    public List<Payment> getAllPayments(){
        return paymentRepository.findAll();
    }


    public Optional<Payment> findPaymentById(Long paymentId){
        return paymentRepository.findById(paymentId);
    }

    @Transactional
    public Payment makePayment(PaymentDTO paymentDTO){
        Account senderAccount = accountRepository.findByAgency(paymentDTO.getFromAgency())
                .orElseThrow(()-> new NoSuchElementException("Sender account not found"));
        Account receiverAccount = accountRepository.findByAgency(paymentDTO.getToAgency())
                .orElseThrow(()-> new NoSuchElementException("Receiver account not found"));

        BigDecimal amount = paymentDTO.getAmount();

        if(senderAccount.getBalance().compareTo(amount) < 0){
            throw new IllegalArgumentException("Insuficient funds in sender account");
        }

        senderAccount.setBalance(senderAccount.getBalance().subtract(amount));
        receiverAccount.setBalance(receiverAccount.getBalance().add(amount));

        Payment newPayment = new Payment();
        newPayment.setAmount(amount);
        newPayment.setSenderAccountId(senderAccount.getId());
        newPayment.setReceiverAccountId(receiverAccount.getId());
        newPayment.setStatus(PaymentStatus.COMPLETED);

        sendNotification("Notification message");

        return paymentRepository.save(newPayment);


    }

    private void sendNotification(String message) {
        producerTemplate.sendBody("seda:notify", message);
    }


    private Account getAccountByAgency(String agency){
        return accountRepository.findByAgency(agency)
                .orElseThrow(() -> new IllegalArgumentException("Account not found for agency: " + agency));
    }

    @Transactional
    public void cancelPayment(Long paymentId){
        Payment deletePayment = paymentRepository.findById(paymentId)
                .orElseThrow(()-> new NoSuchElementException("Payment not found"));

        paymentRepository.delete(deletePayment);
    }

}
