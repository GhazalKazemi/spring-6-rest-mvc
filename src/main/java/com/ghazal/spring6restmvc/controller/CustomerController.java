package com.ghazal.spring6restmvc.controller;

import com.ghazal.spring6restmvc.model.CustomerDTO;
import com.ghazal.spring6restmvc.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CustomerController {

    public static final String CUSTOMER_PATH = "/api/v1/customers";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{id}";

    private final CustomerService customerService;



    @GetMapping(CUSTOMER_PATH)
    public List<CustomerDTO> getCustomers(){
        log.debug("Inside getCustomers controller");
        return customerService.getCustomers();
    }
    @GetMapping(CUSTOMER_PATH_ID)
    public CustomerDTO getCustomerById(@PathVariable UUID id){
        log.debug("Inside getCustomerById controller");
        return customerService.getCustomerById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity<String> addCustomer(@RequestBody CustomerDTO customer){
        log.debug("Inside addCustomer controller");
        CustomerDTO addedCustomer = customerService.addCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customers/" + addedCustomer.getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<String> updateCustomerById(@PathVariable("id") UUID id, @RequestBody CustomerDTO customer){
        log.debug("Inside updateCustomerById controller");
        customerService.updateCustomerById(id, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<String> deleteCustomerById(@PathVariable("id")UUID id){
        log.debug("Inside deleteCustomerById controller");
        customerService.deleteCustomerById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<String> partialUpdateCustomerById(@PathVariable("id") UUID id, @RequestBody CustomerDTO customer){
        log.debug("Inside partialUpdateCustomerById controller");
        customerService.partialUpdateCustomerById(id, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
