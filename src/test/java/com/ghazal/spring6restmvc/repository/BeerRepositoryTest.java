package com.ghazal.spring6restmvc.repository;

import com.ghazal.spring6restmvc.entity.Beer;
import com.ghazal.spring6restmvc.model.BeerStyle;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testAddBeer(){
        Beer beer = beerRepository.save(Beer.builder()
                .beerName("Test Beer")
                        .beerStyle(BeerStyle.PALE_ALE)
                        .upc("8765678")
                        .price(new BigDecimal("11.99"))
                .build());
        beerRepository.flush();
        assertThat(beer.getId()).isNotNull();
    }
    @Test
    void testAddBeerWhenBeerNameIsTooLong(){ //50

        assertThrows(ConstraintViolationException.class, () -> {
            Beer beer = beerRepository.save(Beer.builder()
                    .beerName("Test Beer 0987654321234567890 0987654321234567890 0987654321234567890 0987654321234567890 0987654321234567890")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("8765678")
                    .price(new BigDecimal("11.99"))
                    .build());
            beerRepository.flush();
        });
    }

}