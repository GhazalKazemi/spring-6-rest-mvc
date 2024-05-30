package com.ghazal.spring6restmvc.controller;


import com.ghazal.spring6restmvc.model.Beer;
import com.ghazal.spring6restmvc.service.BeerService;
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
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{id}";

    private final BeerService beerService;

    @GetMapping(BEER_PATH)
    public List<Beer> listBeers(){
        log.debug("Inside listBeers controller");
        return beerService.listBeers();
    }
    @GetMapping(BEER_PATH_ID)
    public Beer getBeerById(@PathVariable("id") UUID id){
        log.debug("Inside getBeerById controller, Beer Id: {}", id.toString());
        return beerService.getBeerById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping(BEER_PATH)
    public ResponseEntity<String> addBeer(@RequestBody Beer beer){
        log.debug("Inside addBeer controller");
        Beer addedBeer = beerService.addBeer(beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + addedBeer.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity<String> updateBeerById(@PathVariable("id") UUID id, @RequestBody Beer beer){
        log.debug("Inside updateBeerById controller");
        beerService.updateBeerById(id, beer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity<String> deleteBeerById(@PathVariable("id") UUID id){
        log.debug("Inside deleteBeerById controller");
        beerService.deleteBeerById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity<String> partialUpdateBeerById(@PathVariable("id") UUID id, @RequestBody Beer beer){
        log.debug("Inside partialUpdateBeerById controller");
        beerService.partialUpdateBeerById(id, beer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
