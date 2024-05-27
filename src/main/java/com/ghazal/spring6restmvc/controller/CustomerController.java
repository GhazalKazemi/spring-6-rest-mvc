package com.ghazal.spring6restmvc.controller;

import com.ghazal.spring6restmvc.model.Customer;
import com.ghazal.spring6restmvc.service.CustomerService;
import com.ghazal.spring6restmvc.service.CustomerServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer){
        log.debug("Inside addCustomer controller");
        Customer addedCustomer = customerService.addCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customers/" + addedCustomer.getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateCustomerById(@PathVariable("id") UUID id, @RequestBody Customer customer){
        log.debug("Inside updateCustomerById controller");
        customerService.updateCustomerById(id, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable("id")UUID id){
        log.debug("Inside deleteCustomerById controller");
        customerService.deleteCustomerById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PatchMapping("{id}")
    public ResponseEntity<String> partialUpdateCustomerById(@PathVariable("id") UUID id, @RequestBody Customer customer){
        log.debug("Inside partialUpdateCustomerById controller");
        customerService.partialUpdateCustomerById(id, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
