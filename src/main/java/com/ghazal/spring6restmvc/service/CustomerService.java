package com.ghazal.spring6restmvc.service;

import com.ghazal.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<CustomerDTO> getCustomers();
    Optional<CustomerDTO> getCustomerById(UUID id);

    CustomerDTO addCustomer(CustomerDTO customer);

    Optional<CustomerDTO> updateCustomerById(UUID id, CustomerDTO customer);

    Boolean deleteCustomerById(UUID id);

    void partialUpdateCustomerById(UUID id, CustomerDTO customer);
}
