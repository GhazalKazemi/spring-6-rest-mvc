package com.ghazal.spring6restmvc.bootstrap;

import com.ghazal.spring6restmvc.entity.Beer;
import com.ghazal.spring6restmvc.entity.Customer;
import com.ghazal.spring6restmvc.model.BeerCSVRecord;
import com.ghazal.spring6restmvc.model.BeerStyle;
import com.ghazal.spring6restmvc.repository.BeerRepository;
import com.ghazal.spring6restmvc.repository.CustomerRepository;
import com.ghazal.spring6restmvc.service.BeerCSVService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;

    private final CustomerRepository customerRepository;

    private final BeerCSVService beerCSVService;


    @Transactional
    @Override
    public void run(String... args) throws Exception {
        loadBeers();
        loadCustomers();
        loadCSVData();
    }

    private void loadCSVData() throws FileNotFoundException {
        if (beerRepository.count() < 10){
            File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");

            List<BeerCSVRecord> beerRecords = beerCSVService.convertCSV(file);

            beerRecords.forEach(beerCSVRecord -> {
                BeerStyle beerStyle = switch (beerCSVRecord.getStyle()) {
                    case "American Pale Lager" -> BeerStyle.LAGER;
                    case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" ->
                            BeerStyle.ALE;
                    case "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA;
                    case "American Porter" -> BeerStyle.PORTER;
                    case "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT;
                    case "Saison / Farmhouse Ale" -> BeerStyle.SAISON;
                    case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT;
                    case "English Pale Ale" -> BeerStyle.PALE_ALE;
                    default -> BeerStyle.PILSNER;
                };

                beerRepository.save(Beer.builder()
                        .beerName(StringUtils.abbreviate(beerCSVRecord.getBeer(), 50))
                        .beerStyle(beerStyle)
                        .price(BigDecimal.TEN)
                        .upc(beerCSVRecord.getRow().toString())
                        .quantityOnHand(beerCSVRecord.getCount())
                        .build());
            });
        }
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
