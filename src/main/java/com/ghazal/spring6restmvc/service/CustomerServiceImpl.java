package com.ghazal.spring6restmvc.service;

import com.ghazal.spring6restmvc.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    private Map<UUID,Customer> customerMap;

    public CustomerServiceImpl(){
        this.customerMap = new HashMap<>();
        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("John Gray")
                .version("1")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Mary Public")
                .version("1")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer customer3 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Anthony Hopkins")
                .version("1")
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);


    }

    @Override
    public List<Customer> getCustomers() {
        log.debug("Inside get all customers service");
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getCustomerById(UUID id) {
        log.debug("Inside getCustomerById service, customerId: {}", id);
        return customerMap.get(id);
    }

}
