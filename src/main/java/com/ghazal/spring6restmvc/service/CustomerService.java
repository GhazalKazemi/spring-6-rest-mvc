package com.ghazal.spring6restmvc.service;

import com.ghazal.spring6restmvc.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<Customer> getCustomers();
    Optional<Customer> getCustomerById(UUID id);

    Customer addCustomer(Customer customer);

    void updateCustomerById(UUID id, Customer customer);

    void deleteCustomerById(UUID id);

    void partialUpdateCustomerById(UUID id, Customer customer);
}
