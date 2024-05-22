package com.ghazal.spring6restmvc.service;

import com.ghazal.spring6restmvc.model.Beer;
import com.ghazal.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    @Override
    public Beer getBeerById(UUID id) {
        log.debug("Inside getBeerById() in service with Beer Id: {}", id.toString());
        return Beer.builder()
                .id(id)
                .version(1)
                .beerName("Balter")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123456")
                .quantityOnHand(121)
                .price(new BigDecimal("10.99"))
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }
}
