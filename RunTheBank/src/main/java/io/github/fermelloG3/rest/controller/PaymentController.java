package io.github.fermelloG3.rest.controller;

import io.github.fermelloG3.domain.entity.Payment;
import io.github.fermelloG3.rest.dto.PaymentDTO;
import io.github.fermelloG3.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/payment")


public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @Operation(summary = "Listar", description = "Metodo para listar todos os pagamentos", tags = "Payments")
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments(){
        List<Payment> payments = paymentService.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @Operation(summary = "Buscar por id", description = "Metodo para listar pagamentos por id", tags = "Payments")
    @GetMapping("/{paymentId}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long paymentId){
        Optional<Payment> payments = paymentService.findPaymentById(paymentId);
        return payments.map(value -> new ResponseEntity<>(value,HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @Operation(summary = "Relizar pagamento", description = "Metodo para realizar pagamento", tags = "Payments")
    @PostMapping
    public ResponseEntity<Payment> makePayment(@RequestBody PaymentDTO paymentDTO){
        try {
            Payment newPayment = paymentService.makePayment(paymentDTO);
            return new ResponseEntity<>(newPayment, HttpStatus.CREATED);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @Operation(summary = "Cancelar", description = "Metodo para cancelar pagamento", tags = "Payments")
    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> cancelPayment(@PathVariable Long paymentId) {
        try {
            paymentService.cancelPayment(paymentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    }

