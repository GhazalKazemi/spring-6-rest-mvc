package com.ghazal.spring6restmvc.repository;

import com.ghazal.spring6restmvc.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testAddCustomer(){
        Customer customer = customerRepository.save(Customer.builder()
                        .customerName("Test Customer")
                .build());
        assertThat(customer.getId()).isNotNull();
    }

}