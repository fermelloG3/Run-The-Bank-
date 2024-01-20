package io.github.fermelloG3;

import io.github.fermelloG3.domain.entity.Customer;
import io.github.fermelloG3.domain.repository.AccountRepository;
import io.github.fermelloG3.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RunTheBankApplication implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;
    public static void main(String[] args){
        SpringApplication.run(RunTheBankApplication.class, args);

    }

    @Override
    public void run(String ...args) throws Exception{
        System.out.println("Creando Clientes");
        Customer cliente1 = new Customer("Fernando","4.446.607-6");
        Customer cliente2 = new Customer("Valentina","5.282.022-0");

        System.out.println("Mostrando Clientes");
        System.out.println(cliente1.getName());
        System.out.println(cliente2.getDocument());

        customerRepository.save(cliente1);





    }
}
