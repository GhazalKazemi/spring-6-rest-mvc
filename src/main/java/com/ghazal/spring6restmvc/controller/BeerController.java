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
@RequestMapping("/api/v1/beer")
public class BeerController {

    private final BeerService beerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> listBeers(){
        log.debug("Inside listBeers controller");
        return beerService.listBeers();
    }
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Beer getBeerById(@PathVariable("id") UUID id){
        log.debug("Inside getBeerById controller, Beer Id: {}", id.toString());
        return beerService.getBeerById(id);
    }

    @PostMapping
    public ResponseEntity<String> addBeer(@RequestBody Beer beer){
        log.debug("Inside addBeer controller");
        Beer addedBeer = beerService.addBeer(beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + addedBeer.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateBeerById(@PathVariable("id") UUID id, @RequestBody Beer beer){
        log.debug("Inside updateBeerById controller");
        beerService.updateBeerById(id, beer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBeerById(@PathVariable("id") UUID id){
        log.debug("Inside deleteBeerById controller");
        beerService.deleteBeerById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PatchMapping("{id}")
    public ResponseEntity<String> partialUpdateBeerById(@PathVariable("id") UUID id, @RequestBody Beer beer){
        log.debug("Inside partialUpdateBeerById controller");
        beerService.partialUpdateBeerById(id, beer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
