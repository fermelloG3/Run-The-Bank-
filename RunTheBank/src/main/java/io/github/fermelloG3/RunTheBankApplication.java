package io.github.fermelloG3;

import io.github.fermelloG3.domain.entity.Customer;
import io.github.fermelloG3.domain.repository.AccountRepository;
import io.github.fermelloG3.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RunTheBankApplication {
    public static void main(String[] args){
        SpringApplication.run(RunTheBankApplication.class, args);

    }

    }

