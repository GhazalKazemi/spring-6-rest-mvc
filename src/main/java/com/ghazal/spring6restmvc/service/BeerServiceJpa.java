package com.ghazal.spring6restmvc.service;

import com.ghazal.spring6restmvc.mapper.BeerMapper;
import com.ghazal.spring6restmvc.model.BeerDTO;
import com.ghazal.spring6restmvc.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJpa implements BeerService {

    private final BeerRepository beerRepository;

    private final BeerMapper beerMapper;
    @Override
    public List<BeerDTO> listBeers() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public BeerDTO addBeer(BeerDTO beer) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beer)));
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID id, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();
        beerRepository.findById(id).ifPresentOrElse(beerToUpdate -> {
            beerToUpdate.setBeerName(beer.getBeerName());
            beerToUpdate.setBeerStyle(beer.getBeerStyle());
            beerToUpdate.setQuantityOnHand(beer.getQuantityOnHand());
            beerToUpdate.setPrice(beer.getPrice());
            beerToUpdate.setUpc(beer.getUpc());
            beerToUpdate.setUpdateDate(LocalDateTime.now());
            atomicReference.set(Optional.of(beerMapper.beerToBeerDto(beerRepository.save(beerToUpdate))));

        }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    @Override
    public Boolean deleteBeerById(UUID id) {
        if(beerRepository.existsById(id)) {
            beerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void partialUpdateBeerById(UUID id, BeerDTO beer) {

    }
}
