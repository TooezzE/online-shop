package ru.skypro.homework.service;

import org.springframework.data.jpa.repository.JpaRepository;
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
    private final ImageService imageService;
    private final AdMapper mapper;

    public AdService(AdRepository adRepository, UserRepository userRepository, ImageService imageService, AdMapper mapper) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.imageService = imageService;
        this.mapper = mapper;
    }
    /**
     ** Метод для получения всех объявлений в виде DTO моделей
     *      * Метод использует {@link JpaRepository#findAll()}
     *      * @return возвращает все объявления из БД
     */


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
    /**
     * Метод для добавления нового объявления в БД
     * Метод использует {@link UserRepository#findByEmail(String)}
     *  {@link ImageService#addImage(MultipartFile)}
     *  {@link JpaRepository#save(Object)}
     * @param file - фотография объявления
     * @return возвращает объявление в качестве DTO модели
     */

    public CreateOrUpdateAd createAd(String username, CreateOrUpdateAd createOrUpdateAd, MultipartFile file) throws IOException {
        User user = userRepository.findByEmail(username);
        Ad ad = new Ad();
        ad.setUser(user);
        ad.setDescription(createOrUpdateAd.getDescription());
        ad.setEmail(username);
        ad.setPrice(createOrUpdateAd.getPrice());
        ad.setTitle(createOrUpdateAd.getTitle());
        ad.setImage(imageService.addImage(file));
        adRepository.save(ad);
        return createOrUpdateAd;
    }
    /**
     * Метод для получения информации об объявлении по id
     * Метод использует {@link AdRepository#findById(Object)}
     *  {@link AdMapper#adToExtendedAd(Ad)}
     * @param id - id объявления
     * @return возвращает DTO модель объявления
     */
    public ExtendedAd getAd(Integer id) {
        Ad ad = adRepository.findById(id).orElseThrow();
        return mapper.adToExtendedAd(ad);
    }
    /**
     * Метод для удаления объявления по id
     * Метод использует {@link AdRepository#findById(Object)}
     * {@link AdRepository#deleteById(Object)}
     * @param id - id объявления
     */
    public void deleteAd(String username, Integer id) {
        Ad ad = adRepository.findById(id).orElseThrow(AdNotFoundException::new);
        User user = userRepository.findByEmail(username);
        if(!user.getUserAds().contains(ad) || !user.getRole().getAuthority().equals("ROLE_ADMIN")) {
            throw new ForbiddenAccessException();
        } else  {
            adRepository.deleteById(id);
        }
    }
    /**
     * Метод для изменения объявления
     * Метод использует {@link AdRepository#findById(Object)}
     * {@link JpaRepository#save(Object)}
     * {@link AdMapper#toDTO(Ad)}
     * @param id - id объявления
     * @param newAd  - DTO модель класса {@link CreateOrUpdateAd};
     * @return возвращает DTO модель объявления
     */
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
    /**
     * Метод получения всех объявлений данного пользователя
     * Метод использует {@link UserRepository#findByEmail(String)}
     * @return возвращает DTO модель объявления пользователя
     */
    @Transactional
    public Ads getAllAdsOfUser(String username) {
        User user = userRepository.findByEmail(username);
        Ads ads = new Ads();
        ads.setResults(user.getUserAds().stream().map(a -> mapper.toDTO(a)).collect(Collectors.toList()));
        ads.setCount(user.getUserAds().size());
        return ads;
    }
    /**
     * Метод изменения фотографии у объявления
     * Метод использует {@link JpaRepository#findById(Object)}
     * {@link ImageService#addImage(MultipartFile)}
     * {@link ImageService#deleteImage(Integer)}
     * {@link JpaRepository#save(Object)}
     * @param id - id объявления
     * @param file - фотография объявления
     */
    @Transactional
    public void updateAdImage(String username, Integer id, MultipartFile file) {
        Ad ad = adRepository.findById(id).orElseThrow(AdNotFoundException::new);
        User user = userRepository.findByEmail(username);
        if(!user.getUserAds().contains(ad)) {
            throw new ForbiddenAccessException();
        }
        Integer imageId = ad.getImage().getId();
        ad.setImage(imageService.addImage(file));
        imageService.deleteImage(imageId);
        adRepository.save(ad);
    }
}
