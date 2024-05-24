package com.ghazal.spring6restmvc.service;

import com.ghazal.spring6restmvc.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> getCustomers();
    Customer getCustomerById(UUID id);

    Customer addCustomer(Customer customer);
}
