package com.ghazal.spring6restmvc.controller;

import com.ghazal.spring6restmvc.entity.Beer;
import com.ghazal.spring6restmvc.model.BeerDTO;
import com.ghazal.spring6restmvc.repository.BeerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BeerControllerIntegrationTest {
    @Autowired
    BeerController beerController;
    @Autowired
    BeerRepository beerRepository;

    @Test
    void testListBeers(){
        List<BeerDTO> beerDTOs = beerController.listBeers();
        assertThat(beerDTOs.size()).isEqualTo(3);
    }
    @Rollback
    @Transactional
    @Test
    void testEmptyBeerList(){
        beerRepository.deleteAll();
        List<BeerDTO> beerDTOs = beerController.listBeers();
        assertThat(beerDTOs.size()).isEqualTo(0);
    }
    @Test
    void testGetBeerById(){
        Beer beer = beerRepository.findAll().get(1);
        BeerDTO beerDTO = beerController.getBeerById(beer.getId());
        assertThat(beerDTO).isNotNull();

    }
    @Test
    void testGetBeerByIdNotFound(){
        assertThrows(NotFoundException.class, () -> {
            beerController.getBeerById(UUID.randomUUID());
        });
    }


}