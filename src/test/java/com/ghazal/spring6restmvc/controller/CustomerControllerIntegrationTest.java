package com.ghazal.spring6restmvc.controller;

import com.ghazal.spring6restmvc.entity.Customer;
import com.ghazal.spring6restmvc.model.CustomerDTO;
import com.ghazal.spring6restmvc.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIntegrationTest {
    @Autowired
    CustomerController customerController;
    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testListCustomers(){
        List<CustomerDTO> customers = customerController.getCustomers();
        assertThat(customers.size()).isEqualTo(3);
    }
    @Rollback
    @Transactional
    @Test
    void testEmptyCustomersList(){
        customerRepository.deleteAll();
        List<CustomerDTO> customers = customerController.getCustomers();
        assertThat(customers.size()).isEqualTo(0);
    }

    @Test
    void testFindCustomerById(){
        Customer customer = customerRepository.findAll().get(1);
        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());
        assertThat(customerDTO).isNotNull();
    }

    @Test
    void testFindCustomerByIdNotFound(){
        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });
    }
    @Rollback
    @Transactional
    @Test
    void testAddCustomer(){
        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerName("New Test Customer")
                .build();
        ResponseEntity<String> responseEntity = customerController.addCustomer(customerDTO);

        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));

        String[] addCustomerPath = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID uuidFromHeader = UUID.fromString(addCustomerPath[4]);

        Customer customer = customerRepository.findById(uuidFromHeader).get();
        assertThat(customer).isNotNull();
    }
}