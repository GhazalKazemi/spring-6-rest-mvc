package com.ghazal.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghazal.spring6restmvc.entity.Beer;
import com.ghazal.spring6restmvc.mapper.BeerMapper;
import com.ghazal.spring6restmvc.model.BeerDTO;
import com.ghazal.spring6restmvc.repository.BeerRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BeerControllerIntegrationTest {
    public static final String UPDATED_BEER_NAME = "Updated Beer Name";
    public static final String NEW_TEST_BEER = "New Test Beer";
    @Autowired
    BeerController beerController;
    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

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
                .beerName(NEW_TEST_BEER)
                .build();
        ResponseEntity<String> responseEntity = beerController.addBeer(beerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] addBeerPath = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID uuidSegmentFromPath = UUID.fromString(addBeerPath[4]);

        Beer beer = beerRepository.findById(uuidSegmentFromPath).get();
        assertThat(beer).isNotNull();
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateExistingBeer(){
        Beer existingBeer = beerRepository.findAll().get(1);
        BeerDTO beerDTO = beerMapper.beerToBeerDto(existingBeer);
        beerDTO.setId(null);
        beerDTO.setVersion(null);

        beerDTO.setBeerName(UPDATED_BEER_NAME);

        ResponseEntity<String> responseEntity = beerController.updateBeerById(existingBeer.getId(), beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer updatedBeer = beerRepository.findById(existingBeer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(UPDATED_BEER_NAME);
    }
    @Test
    void testUpdateExistingBeerNotFound(){
        assertThrows(NotFoundException.class, () -> {
            beerController.updateBeerById(UUID.randomUUID(), BeerDTO.builder().build());
        });
    }
    @Rollback
    @Transactional
    @Test
    void testDeleteBeerById(){
        Beer beer = beerRepository.findAll().get(2);
        ResponseEntity<String> responseEntity = beerController.deleteBeerById(beer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(beerRepository.findById(beer.getId())).isEmpty();
    }
    @Test
    void testDeleteBeerByIdNotFound(){
        assertThrows(NotFoundException.class, () -> {
            beerController.deleteBeerById(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testPartialUpdateById() throws Exception {
        Beer beer = beerRepository.findAll().get(1);

        Map<String, Object> nameMap = new HashMap<>();
        nameMap.put("beerName", "Patched Name 0987654321234567890 0987654321234567890 0987654321234567890 0987654321234567890 0987654321234567890");

        mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nameMap)))
                .andExpect(status().isBadRequest());

        }


}