package com.ghazal.spring6restmvc.service;

import com.ghazal.spring6restmvc.model.Beer;
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

    private Map<UUID, Beer> beerMap;

    public BeerServiceImpl(){
        this.beerMap = new HashMap<>();
        Beer balterPaleAle = Beer.builder()
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

        Beer carltonWheat = Beer.builder()
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
        Beer pureBlondeLager = Beer.builder()
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
    public List<Beer> listBeers(){
        log.debug("Inside listBeers service");
        return new ArrayList<>(beerMap.values());
    }


    @Override
    public Beer getBeerById(UUID id) {
        log.debug("Inside getBeerById() in service with Beer Id: {}", id.toString());
        return beerMap.get(id);
    }

    @Override
    public Beer addBeer(Beer beer) {
        log.debug("Inside addBeer service");
        Beer addedBeer = Beer.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .version(beer.getVersion())
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
    public void updateBeerById(UUID id, Beer beer) { //replace the entire beer object with new object
        log.debug("Inside updateBeerById service");
        Beer existingBeer = beerMap.get(id);
        if (existingBeer != null){
            existingBeer.setBeerName(beer.getBeerName());
            existingBeer.setBeerStyle(beer.getBeerStyle());
            existingBeer.setUpdateDate(LocalDateTime.now());
            existingBeer.setVersion(existingBeer.getVersion() + 1);
            existingBeer.setPrice(beer.getPrice());
            existingBeer.setUpc(beer.getUpc());
            existingBeer.setQuantityOnHand(beer.getQuantityOnHand());
            beerMap.put(existingBeer.getId(), existingBeer);
        }

    }

    @Override
    public void deleteBeerById(UUID id) {
        log.debug("Inside deleteBeerById service");
        this.beerMap.remove(id);
    }

    @Override
    public void partialUpdateBeerById(UUID id, Beer beer) { // patch some beer properties on an existing beer
        log.debug("Inside partialUpdateBeerById service");
        Beer existingBeer = beerMap.get(id);
        if (existingBeer != null){
            if(StringUtils.hasText(beer.getBeerName())){
                existingBeer.setBeerName(beer.getBeerName());
            }
            if (beer.getBeerStyle() != null){
                existingBeer.setBeerStyle(beer.getBeerStyle());
            }
            if (beer.getQuantityOnHand() != null){
                existingBeer.setQuantityOnHand(beer.getQuantityOnHand());
            }
            if (beer.getPrice() != null){
                existingBeer.setPrice(beer.getPrice());
            }
            if(StringUtils.hasText(beer.getUpc())){
                existingBeer.setUpc(beer.getUpc());
            }
            if (beer.getVersion() != null){
                existingBeer.setVersion(beer.getVersion());
            }
            existingBeer.setUpdateDate(LocalDateTime.now());
        }
    }
}
