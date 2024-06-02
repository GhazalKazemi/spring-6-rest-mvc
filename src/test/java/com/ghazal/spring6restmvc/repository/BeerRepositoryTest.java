package com.ghazal.spring6restmvc.repository;

import com.ghazal.spring6restmvc.entity.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testAddBeer(){
        Beer beer = beerRepository.save(Beer.builder()
                .beerName("Test Beer")
                .build());
        assertThat(beer.getId()).isNotNull();
    }

}