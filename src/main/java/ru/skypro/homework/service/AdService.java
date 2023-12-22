package ru.skypro.homework.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exceptions.AdNotFoundException;
import ru.skypro.homework.exceptions.ForbiddenAccessException;
import ru.skypro.homework.mappers.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdService {

    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final AdMapper mapper;

    public AdService(AdRepository adRepository, UserRepository userRepository, ImageRepository imageRepository, AdMapper mapper) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.mapper = mapper;
    }

    @Transactional
    public Ads getAllAds() {
        List<AdDTO> adList = adRepository.findAll().stream()
                .map(a -> mapper.toDTO(a))
                .collect(Collectors.toList());
        Ads ads = new Ads();
        ads.setResults(adList);
        ads.setCount(adList.size());
        return ads;
    }

    public CreateOrUpdateAd createAd(String username, CreateOrUpdateAd createOrUpdateAd, MultipartFile file) throws IOException {
        User user = userRepository.findByEmail(username);
        Ad ad = new Ad();
        ad.setUser(user);
        ad.setDescription(createOrUpdateAd.getDescription());
        ad.setEmail(username);
        ad.setPrice(createOrUpdateAd.getPrice());
        ad.setTitle(createOrUpdateAd.getTitle());
        ad.setImage(imageRepository.save(toImageEntity(file)));
        adRepository.save(ad);
        return createOrUpdateAd;
    }

    public ExtendedAd getAd(Integer id) {
        Ad ad = adRepository.findById(id).orElseThrow();
        return mapper.adToExtendedAd(ad);
    }

    public void deleteAd(String username, Integer id) {
        Ad ad = adRepository.findById(id).orElseThrow(AdNotFoundException::new);
        User user = userRepository.findByEmail(username);
        if(!user.getUserAds().contains(ad) || !user.getAuthorities().contains("ROLE_ADMIN")) {
            throw new ForbiddenAccessException();
        } else  {
            adRepository.deleteById(id);
        }
    }

    public AdDTO editAd(String username, Integer id, CreateOrUpdateAd newAd) {
        Ad ad = adRepository.findById(id).orElseThrow(AdNotFoundException::new);
        User user = userRepository.findByEmail(username);
        if(!user.getUserAds().contains(ad)) {
            throw new ForbiddenAccessException();
        } else {
            ad.setTitle(newAd.getTitle());
            ad.setDescription(newAd.getDescription());
            ad.setPrice(newAd.getPrice());
            adRepository.save(ad);
        }
        return mapper.toDTO(ad);
    }

    @Transactional
    public Ads getAllAdsOfUser(String username) {
        User user = userRepository.findByEmail(username);
        Ads ads = new Ads();
        ads.setResults(user.getUserAds().stream().map(a -> mapper.toDTO(a)).collect(Collectors.toList()));
        ads.setCount(user.getUserAds().size());
        return ads;
    }

    public byte[] updateAdImage(String username, Integer id, MultipartFile file) throws IOException {
        Ad ad = adRepository.findById(id).orElseThrow(AdNotFoundException::new);
        User user = userRepository.findByEmail(username);
        if(!user.getUserAds().contains(ad)) {
            throw new ForbiddenAccessException();
        } else {
            Image image = imageRepository.save(toImageEntity(file));
            ad.setImage(image);
            adRepository.save(ad);
        }
        return file.getBytes();
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setFileSize(file.getSize());
        image.setBytes(file.getBytes());
        image.setContentType(file.getContentType());
        return image;
    }
}
