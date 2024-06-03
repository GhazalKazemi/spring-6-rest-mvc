package com.ghazal.spring6restmvc.mapper;

import com.ghazal.spring6restmvc.entity.Customer;
import com.ghazal.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDTO customerDTO);
    CustomerDTO customerToCustomerDto(Customer customer);
}
