package com.ghazal.spring6restmvc.mapper;

import com.ghazal.spring6restmvc.entity.Beer;
import com.ghazal.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO beerDTO);
    BeerDTO beerToBeerDto(Beer beer);
}
