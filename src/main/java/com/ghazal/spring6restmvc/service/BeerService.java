package com.ghazal.spring6restmvc.service;

import com.ghazal.spring6restmvc.model.Beer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    List<Beer> listBeers();

    Optional<Beer> getBeerById(UUID id);

    Beer addBeer(Beer beer);

    void updateBeerById(UUID id, Beer beer);

    void deleteBeerById(UUID id);

    void partialUpdateBeerById(UUID id, Beer beer);
}
