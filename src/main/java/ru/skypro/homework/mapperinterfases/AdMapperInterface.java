package ru.skypro.homework.mapperinterfases;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.entity.Ad;

import java.util.LinkedHashMap;
import java.util.List;


@Mapper
public interface AdMapperInterface {

    @Mapping(target = "id" ,source = "results")
    List<Ad> toEntity(Ads dto);

    @Mapping(target = "results", source = "id")
    Ads adsDto(Ad entity);

    Ad toEntity(CreateOrUpdateAd createOrUpdateAd);

    CreateOrUpdateAd createDto(Ad entity);

}
