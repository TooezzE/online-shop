package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;

public interface AdService {

    AdsDTO getAllAds();

    AdDTO addAd(CreateOrUpdateAdDTO createOrUpdateAdDTO, MultipartFile image);


    ExtendedAdDTO getAdInfo(Integer adId);

    Void deleteAd(Integer adId);

    AdDTO patchAd(Integer adId, CreateOrUpdateAdDTO createOrUpdateAdDTO);

    AdsDTO getAllAdsByAuthor();


    Void patchAdImage(Integer adId, MultipartFile image);



}

