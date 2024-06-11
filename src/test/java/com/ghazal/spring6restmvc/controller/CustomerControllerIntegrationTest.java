package com.ghazal.spring6restmvc.controller;

import com.ghazal.spring6restmvc.entity.Customer;
import com.ghazal.spring6restmvc.mapper.BeerMapper;
import com.ghazal.spring6restmvc.mapper.CustomerMapper;
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
    public static final String UPDATED_CUSTOMER_NAME = "Updated Customer Name";
    @Autowired
    CustomerController customerController;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

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
    @Rollback
    @Transactional
    @Test
    void updateExistingCustomer(){
        Customer existingCustomer = customerRepository.findAll().get(2);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(existingCustomer);
        customerDTO.setId(null);
        customerDTO.setVersion(null);

        customerDTO.setCustomerName(UPDATED_CUSTOMER_NAME);

        ResponseEntity<String> responseEntity = customerController.updateCustomerById(existingCustomer.getId(), customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer updatedCustomer = customerRepository.findById(existingCustomer.getId()).get();
        assertThat(updatedCustomer.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
    }
    @Test
    void testUpdateExistingCustomerNotFound(){
        assertThrows(NotFoundException.class, () -> {
            customerController.updateCustomerById(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }
    @Rollback
    @Transactional
    @Test
    void testDeleteCustomerById(){
        Customer customer = customerRepository.findAll().get(2);
        ResponseEntity<String> responseEntity = customerController.deleteCustomerById(customer.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customerRepository.findById(customer.getId())).isEmpty();
    }

    @Test
    void testDeleteCustomerByIdNotFound(){
        assertThrows(NotFoundException.class, () -> {
            customerController.deleteCustomerById(UUID.randomUUID());
        });
    }

}