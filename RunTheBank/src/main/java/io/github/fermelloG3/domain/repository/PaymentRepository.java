package io.github.fermelloG3.domain.repository;

import io.github.fermelloG3.domain.entity.Account;
import io.github.fermelloG3.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {


}
