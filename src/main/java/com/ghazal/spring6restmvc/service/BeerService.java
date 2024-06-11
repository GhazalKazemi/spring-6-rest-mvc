package com.ghazal.spring6restmvc.service;

import com.ghazal.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    List<BeerDTO> listBeers();

    Optional<BeerDTO> getBeerById(UUID id);

    BeerDTO addBeer(BeerDTO beer);

    Optional<BeerDTO> updateBeerById(UUID id, BeerDTO beer);

    Boolean deleteBeerById(UUID id);

    void partialUpdateBeerById(UUID id, BeerDTO beer);
}
