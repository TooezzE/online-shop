package ru.skypro.homework.mappers;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.Ad;

@Service
public class AdMapper {

        public Ad toEntity(AdDTO dto) {
            Ad ad = new Ad();
            ad.setId(dto.getPk());
            ad.setTitle(dto.getTitle());
            ad.setPrice(dto.getPrice());

            return ad; // not all fields
        }

        public AdDTO toDTO(Ad entity) {
            AdDTO dto = new AdDTO();
            dto.setPk(entity.getId());
            dto.setAuthor(entity.getUser().getId());
            //dto.setImage(entity.getImage().getName());
            dto.setTitle(entity.getTitle());
            dto.setPrice(entity.getPrice());

            return dto;
        }

        public Ad createOrUpdateToEntity(CreateOrUpdateAd dto) {
            Ad ad = new Ad();
            ad.setTitle(dto.getTitle());
            ad.setDescription(dto.getDescription());
            ad.setPrice(dto.getPrice());

            return ad; // not all fields
        }

        public CreateOrUpdateAd adToCreateOrUpdate(Ad ad) {
            CreateOrUpdateAd dto = new CreateOrUpdateAd();
            dto.setTitle(ad.getTitle());
            dto.setPrice(ad.getPrice());
            dto.setDescription(ad.getDescription());

            return dto;
        }

        public ExtendedAd adToExtendedAd(Ad ad) {
            ExtendedAd dto = new ExtendedAd();
            dto.setPk(ad.getId());
            dto.setDescription(ad.getDescription());
            dto.setEmail(ad.getEmail());
            dto.setTitle(ad.getTitle());
            dto.setPrice(ad.getPrice());

            return dto;
        }

        public Ad extendedAdToAd(ExtendedAd extendedAd) {
            Ad ad = new Ad();
            ad.setId(extendedAd.getPk());
            ad.setPrice(extendedAd.getPrice());
            ad.setDescription(extendedAd.getDescription());
            ad.setTitle(extendedAd.getTitle());
            ad.setEmail(extendedAd.getEmail());

            return ad; // not all fields
        }

}
