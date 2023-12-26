package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mappers.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.ImageService;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ImageService imageService;

    public AdServiceImpl(AdRepository adRepository,
                         UserRepository userRepository,
                         CommentRepository commentRepository,
                         ImageService imageService) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.imageService = imageService;
    }
    /**
     * Метод для получения всех объявлений в виде DTO моделей
     * Метод использует {@link JpaRepository#findAll()}
     * @return возвращает все объявления из БД
     */
    //Метод для получения всех объявлений в виде DTO моделей
    @Override
    public AdsDTO getAllAds() {
        log.info("Fetching all ads");
        AdsDTO adsDTO = new AdsDTO();
        List<AdDTO> list = new ArrayList<>();
        adRepository.findAll().forEach(ad -> list.add(AdMapper.INSTANCE.adToAdDTO(ad)));
        adsDTO.setCount(list.size());
        adsDTO.setResults(list);
        return adsDTO;
    }
    /**
     * Метод для добавления нового объявления в БД
     * Метод использует {@link UserRepository#findByEmail(String)}
     *  {@link ImageService#addImage(MultipartFile)}
     *  {@link JpaRepository#save(Object)}
     *  {@link AdMapper#createOrUpdateAdDTOToAd(CreateOrUpdateAdDTO, User)}
     *  {@link AdMapper#adToAdDTO(Ad)}
     * @param createOrUpdateAdDTO - DTO модель класса {@link CreateOrUpdateAdDTO};
     * @param image - фотография объявления
     * @return возвращает объявление в качестве DTO модели
     */
    //Метод для добавления нового объявления
    @Override
    public AdDTO addAd(CreateOrUpdateAdDTO createOrUpdateAdDTO, MultipartFile image) {
        log.info("Creating a new ad");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName());
        Ad ad = AdMapper.INSTANCE.createOrUpdateAdDTOToAd(createOrUpdateAdDTO, user);
        ad.setImage(imageService.addImage(image));
        return AdMapper.INSTANCE.adToAdDTO(adRepository.save(ad));
    }
    /**
     * Метод для получения информации об объявлении по id
     * Метод использует {@link AdRepository#findById}
     *  {@link UserRepository#findById}
     *  {@link AdMapper#toExtendedAdDTO(Ad, User)}
     * @param adId - id объявления
     * @return возвращает DTO модель объявления
     */
    //Метод для получения информации об объявлении по id
    @Override
    public ExtendedAdDTO getAdInfo(Integer adId) {
        log.info("Fetching full details for ad with ID: {}", adId);
        Ad ad = adRepository.findById(adId).orElseThrow(AdNotFoundException::new);
        User user = userRepository.findById(ad.getAuthor().getId()).orElseThrow(UserNotFoundException::new);
        return AdMapper.INSTANCE.toExtendedAdDTO(ad, user);
    }
    /**
     * Метод для удаления объявления по id
     * Метод использует {@link AdRepository#findById}
     * {@link AdRepository#deleteById}
     * {@link CommentRepository#deleteAllByAd_id(Integer)}
     * Метод использует {@link ImageService#deleteImage}
     * @param adId - id объявления
     * @return null
     */
    //Метод для удаления объявления по id
    @Override
    public Void deleteAd(Integer adId) {
        log.info("Removing ad with ID: {}", adId);
        Integer imageId = adRepository.findById(adId).orElseThrow(AdNotFoundException::new).getImage().getId();
        adRepository.deleteById(adId);
        imageService.deleteImage(imageId);
        commentRepository.deleteAllByAd_id(adId);
        return null;
    }
    /**
     * Метод для изменения объявления
     * Метод использует {@link AdRepository#findById}
     * {@link JpaRepository#save(Object)}
     * {@link AdMapper#adToAdDTO(Ad)}
     * @param adId - id объявления
     * @param createOrUpdateAdDTO - DTO модель класса {@link CreateOrUpdateAdDTO};
     * @return возвращает DTO модель объявления
     */
    //Метод для изменения объявления
    @Override
    public AdDTO patchAd(Integer adId, CreateOrUpdateAdDTO createOrUpdateAdDTO) {
        log.info("Updating ad with ID: {}", adId);
        Ad ad = adRepository.findById(adId).orElseThrow(AdNotFoundException::new);
        ad.setTitle(createOrUpdateAdDTO.getTitle());
        ad.setDescription(createOrUpdateAdDTO.getDescription());
        ad.setPrice(createOrUpdateAdDTO.getPrice());
        return AdMapper.INSTANCE.adToAdDTO(adRepository.save(ad));
    }
    /**
     * Метод получения всех объявлений данного пользователя
     * Метод использует {@link UserRepository#findByEmail(String)}
     * {@link AdRepository#findAllByAuthor(User)}
     * @return возвращает DTO модель объявления пользователя
     */
    //Метод получения всех объявлений данного пользователя
    @Override
    public AdsDTO getAllAdsByAuthor() {
        log.info("Fetching ads for the authenticated user");
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<AdDTO> list = new ArrayList<>();
        adRepository.findAllByAuthor(user).forEach(u -> list.add(AdMapper.INSTANCE.adToAdDTO(u)));
        AdsDTO adsDTO = new AdsDTO();
        adsDTO.setCount(list.size());
        adsDTO.setResults(list);
        return adsDTO;
    }
    /**
     * Метод изменения фотографии у объявления
     * Метод использует {@link JpaRepository#findById(Object)}
     * {@link ImageService#addImage(MultipartFile)}
     * {@link ImageService#deleteImage}
     * {@link JpaRepository#save(Object)}
     * @param adId - id объявления
     * @param image - фотография объявления
     */

    // Метод изменения фотографии у объявления
    @Override
    @Transactional
    public Void patchAdImage(Integer adId, MultipartFile image) {
        log.info("Updating image for ad with ID: {}", adId);
        Ad ad = adRepository.findById(adId).orElseThrow(AdNotFoundException::new);
        Integer imageId = ad.getImage().getId();
        ad.setImage(imageService.addImage(image));
        imageService.deleteImage(imageId);
        adRepository.save(ad);
        return null;
    }
}
