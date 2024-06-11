package com.ghazal.spring6restmvc.service;

import com.ghazal.spring6restmvc.mapper.CustomerMapper;
import com.ghazal.spring6restmvc.model.CustomerDTO;
import com.ghazal.spring6restmvc.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
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
    public Optional<CustomerDTO> updateCustomerById(UUID id, CustomerDTO customer) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();
        customerRepository.findById(id).ifPresentOrElse(customerToUpdate -> {
            customerToUpdate.setCustomerName(customer.getCustomerName());
            customerToUpdate.setLastModifiedDate(LocalDateTime.now());
            atomicReference.set(Optional.of(customerMapper.customerToCustomerDto(customerRepository.save(customerToUpdate))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteCustomerById(UUID id) {
        if (customerRepository.existsById(id)){
            customerRepository.deleteById(id);
            return true;
        }
        return false;

    }

    @Override
    public void partialUpdateCustomerById(UUID id, CustomerDTO customer) {

    }
}
