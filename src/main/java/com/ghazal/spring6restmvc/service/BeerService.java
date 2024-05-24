package com.ghazal.spring6restmvc.service;

import com.ghazal.spring6restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {
    List<Beer> listBeers();

    Beer getBeerById(UUID id);

    Beer addBeer(Beer beer);

    void updateBeerById(UUID id, Beer beer);
}
