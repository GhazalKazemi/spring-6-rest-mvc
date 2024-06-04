package com.ghazal.spring6restmvc.service;

import com.ghazal.spring6restmvc.mapper.CustomerMapper;
import com.ghazal.spring6restmvc.model.CustomerDTO;
import com.ghazal.spring6restmvc.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJpa implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.ofNullable(customerMapper.customerToCustomerDto(customerRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public CustomerDTO addCustomer(CustomerDTO customer) {
        return customerMapper.customerToCustomerDto(customerRepository.save(customerMapper.customerDtoToCustomer(customer)));
    }

    @Override
    public void updateCustomerById(UUID id, CustomerDTO customer) {

    }

    @Override
    public void deleteCustomerById(UUID id) {

    }

    @Override
    public void partialUpdateCustomerById(UUID id, CustomerDTO customer) {

    }
}
