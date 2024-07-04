package com.ghazal.spring6restmvc.service;

import com.ghazal.spring6restmvc.model.BeerDTO;
import com.ghazal.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private Map<UUID, BeerDTO> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();
        BeerDTO balterPaleAle = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Balter")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123456")
                .quantityOnHand(121)
                .price(new BigDecimal("10.99"))
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDTO carltonWheat = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Carlton Draught")
                .beerStyle(BeerStyle.WHEAT)
                .upc("123456")
                .quantityOnHand(27)
                .price(new BigDecimal("9.99"))
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        BeerDTO pureBlondeLager = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Pure Blonde")
                .beerStyle(BeerStyle.LAGER)
                .upc("123456")
                .quantityOnHand(394)
                .price(new BigDecimal("12.99"))
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        beerMap.put(balterPaleAle.getId(), balterPaleAle);
        beerMap.put(carltonWheat.getId(), carltonWheat);
        beerMap.put(pureBlondeLager.getId(), pureBlondeLager);
    }

    @Override
    public List<BeerDTO> listBeers(String beerName, BeerStyle beerStyle) {
        log.debug("Inside listBeers service");
        return new ArrayList<>(beerMap.values());
    }


    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        log.debug("Inside getBeerById() in service with Beer Id: {}", id.toString());
        return Optional.of(beerMap.get(id));
    }

    @Override
    public BeerDTO addBeer(BeerDTO beer) {
        log.debug("Inside addBeer service");
        BeerDTO addedBeer = BeerDTO.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .quantityOnHand(beer.getQuantityOnHand())
                .build();
        beerMap.put(addedBeer.getId(), addedBeer);
        return addedBeer;
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID id, BeerDTO beer) { //replace the entire beer object with new object
        log.debug("Inside updateBeerById service");
        BeerDTO existingBeer = beerMap.get(id);

        existingBeer.setBeerName(beer.getBeerName());
        existingBeer.setBeerStyle(beer.getBeerStyle());
        existingBeer.setUpdateDate(LocalDateTime.now());
        existingBeer.setPrice(beer.getPrice());
        existingBeer.setUpc(beer.getUpc());
        existingBeer.setQuantityOnHand(beer.getQuantityOnHand());
        beerMap.put(existingBeer.getId(), existingBeer);

        return Optional.of(existingBeer);
    }

    @Override
    public Boolean deleteBeerById(UUID id) {
        log.debug("Inside deleteBeerById service");
        this.beerMap.remove(id);
        return true;
    }

    @Override
    public void partialUpdateBeerById(UUID id, BeerDTO beer) { // patch some beer properties on an existing beer
        log.debug("Inside partialUpdateBeerById service");
        BeerDTO existingBeer = beerMap.get(id);
        if (existingBeer != null) {
            if (StringUtils.hasText(beer.getBeerName())) {
                existingBeer.setBeerName(beer.getBeerName());
            }
            if (beer.getBeerStyle() != null) {
                existingBeer.setBeerStyle(beer.getBeerStyle());
            }
            if (beer.getQuantityOnHand() != null) {
                existingBeer.setQuantityOnHand(beer.getQuantityOnHand());
            }
            if (beer.getPrice() != null) {
                existingBeer.setPrice(beer.getPrice());
            }
            if (StringUtils.hasText(beer.getUpc())) {
                existingBeer.setUpc(beer.getUpc());
            }
            existingBeer.setUpdateDate(LocalDateTime.now());
        }
    }
}
