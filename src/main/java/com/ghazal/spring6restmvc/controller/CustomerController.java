package com.ghazal.spring6restmvc.controller;

import com.ghazal.spring6restmvc.model.Customer;
import com.ghazal.spring6restmvc.service.CustomerService;
import com.ghazal.spring6restmvc.service.CustomerServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> getCustomers(){
        log.debug("Inside getCustomers controller");
        return customerService.getCustomers();
    }
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable UUID id){
        log.debug("Inside getCustomerById controller");
        return customerService.getCustomerById(id);
    }
}
