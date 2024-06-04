package com.ghazal.spring6restmvc.controller;

import com.ghazal.spring6restmvc.entity.Beer;
import com.ghazal.spring6restmvc.model.BeerDTO;
import com.ghazal.spring6restmvc.repository.BeerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

    @Rollback
    @Transactional
    @Test
    void testAddBeer(){
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("New Test Beer")
                .build();
        ResponseEntity<String> responseEntity = beerController.addBeer(beerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] addBeerPath = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID uuidSegmentFromPath = UUID.fromString(addBeerPath[4]);

        Beer beer = beerRepository.findById(uuidSegmentFromPath).get();
        assertThat(beer).isNotNull();
    }


}