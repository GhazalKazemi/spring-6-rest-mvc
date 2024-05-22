package com.ghazal.spring6restmvc.service;

import com.ghazal.spring6restmvc.model.Beer;

import java.util.UUID;

public interface BeerService {
    Beer getBeerById(UUID id);
}
