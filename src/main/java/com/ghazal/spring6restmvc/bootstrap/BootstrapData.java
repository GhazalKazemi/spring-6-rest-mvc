package com.ghazal.spring6restmvc.bootstrap;

import com.ghazal.spring6restmvc.entity.Beer;
import com.ghazal.spring6restmvc.entity.Customer;
import com.ghazal.spring6restmvc.model.BeerStyle;
import com.ghazal.spring6restmvc.repository.BeerRepository;
import com.ghazal.spring6restmvc.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@AllArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;

    private final CustomerRepository customerRepository;


    @Override
    public void run(String... args) throws Exception {
        loadBeers();
        loadCustomers();
    }
    private void loadBeers() {
        if(beerRepository.count() == 0){
            Beer balterPaleAle = Beer.builder()
                    .beerName("Balter")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("123456")
                    .quantityOnHand(121)
                    .price(new BigDecimal("10.99"))
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer carltonWheat = Beer.builder()
                    .beerName("Carlton Draught")
                    .beerStyle(BeerStyle.WHEAT)
                    .upc("123456")
                    .quantityOnHand(27)
                    .price(new BigDecimal("9.99"))
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();
            Beer pureBlondeLager = Beer.builder()
                    .beerName("Pure Blonde")
                    .beerStyle(BeerStyle.LAGER)
                    .upc("123456")
                    .quantityOnHand(394)
                    .price(new BigDecimal("12.99"))
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();
            beerRepository.saveAll(Arrays.asList(pureBlondeLager, carltonWheat, balterPaleAle));
        }
    }


    private void loadCustomers() {
        if(customerRepository.count() == 0){
            Customer customer1 = Customer.builder()
                    .customerName("John Gray")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .customerName("Mary Public")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .customerName("Anthony Hopkins")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
        }
    }


}
