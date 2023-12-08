package ru.skypro.homework.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.ExtendedAd;
import ru.skypro.homework.repository.AdRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdMapper {

    @Autowired
    private AdRepository repository;

    public Ads getAdsDto() {
       Ads ads = new Ads();
       List<Ad> res = new ArrayList<>(repository.findAll());
       ads.setResults(res);
       ads.setCount(res.size());
       return ads;
    }

    public ExtendedAd createOrUpdateAdConverter(CreateOrUpdateAd createOrUpdateAd) {
        ExtendedAd ad = new ExtendedAd();
        ad.setPrice(createOrUpdateAd.getPrice());
        ad.setTitle(createOrUpdateAd.getTitle());
        ad.setDescription(createOrUpdateAd.getDescription());
        return ad;
    }

}
