package com.ghazal.spring6restmvc.service;

import com.ghazal.spring6restmvc.model.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    private Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();
        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("John Gray")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Mary Public")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        CustomerDTO customer3 = CustomerDTO.builder()
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
    public List<CustomerDTO> getCustomers() {
        log.debug("Inside get all customers service");
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        log.debug("Inside getCustomerById service, customerId: {}", id);
        return Optional.ofNullable(customerMap.get(id));
    }

    @Override
    public CustomerDTO addCustomer(CustomerDTO customer) {
        log.debug("Inside addCustomer service");
        CustomerDTO addedCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .customerName(customer.getCustomerName())
                .build();
        customerMap.put(UUID.randomUUID(), addedCustomer);
        return addedCustomer;
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID id, CustomerDTO customer) {
        log.debug("Inside updateCustomerById service");
        CustomerDTO existingCustomer = customerMap.get(id);
        existingCustomer.setCustomerName(customer.getCustomerName());
        existingCustomer.setLastModifiedDate(LocalDateTime.now());

        customerMap.put(existingCustomer.getId(), existingCustomer);

        return Optional.of(existingCustomer);
    }

    @Override
    public Boolean deleteCustomerById(UUID id) {
        log.debug("Inside deleteCustomerById service");
        this.customerMap.remove(id);
        return true;
    }

    @Override
    public void partialUpdateCustomerById(UUID id, CustomerDTO customer) {
        log.debug("Inside partialUpdateCustomerById service");
        CustomerDTO existingCustomer = customerMap.get(id);
        if (existingCustomer != null) {
            if (StringUtils.hasText(customer.getCustomerName())) {
                existingCustomer.setCustomerName(customer.getCustomerName());
            }
            existingCustomer.setLastModifiedDate(LocalDateTime.now());
        }
    }

}
