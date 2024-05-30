package com.ghazal.spring6restmvc.service;

import com.ghazal.spring6restmvc.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Mary Public")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer customer3 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Anthony Hopkins")
                .version(1)
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
    public Optional<Customer> getCustomerById(UUID id) {
        log.debug("Inside getCustomerById service, customerId: {}", id);
        return Optional.ofNullable(customerMap.get(id));
    }

    @Override
    public Customer addCustomer(Customer customer) {
        log.debug("Inside addCustomer service");
        Customer addedCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .version(customer.getVersion())
                .customerName(customer.getCustomerName())
                .build();
        customerMap.put(UUID.randomUUID(), addedCustomer);
        return addedCustomer;
    }

    @Override
    public void updateCustomerById(UUID id, Customer customer) {
        log.debug("Inside updateCustomerById service");
        Customer existingCustomer = customerMap.get(id);
        if (existingCustomer != null){
            existingCustomer.setCustomerName(customer.getCustomerName());
            existingCustomer.setVersion(existingCustomer.getVersion() + 1);
            existingCustomer.setLastModifiedDate(LocalDateTime.now());

            customerMap.put(existingCustomer.getId(), existingCustomer);
        }
    }

    @Override
    public void deleteCustomerById(UUID id) {
        log.debug("Inside deleteCustomerById service");
        this.customerMap.remove(id);
    }

    @Override
    public void partialUpdateCustomerById(UUID id, Customer customer) {
        log.debug("Inside partialUpdateCustomerById service");
        Customer existingCustomer = customerMap.get(id);
        if (existingCustomer != null){
            if (StringUtils.hasText(customer.getCustomerName())){
                existingCustomer.setCustomerName(customer.getCustomerName());
            }
            if (customer.getVersion() != null){
                existingCustomer.setVersion(customer.getVersion());
            }
            existingCustomer.setLastModifiedDate(LocalDateTime.now());
        }
    }

}
