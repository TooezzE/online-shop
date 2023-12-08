package ru.skypro.homework.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.ExtendedAd;

@Controller
@RequestMapping("/ads")
public class AdController {


    @GetMapping
    public Ads getAllAds() {
        return new Ads();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CreateOrUpdateAd createAd(@RequestBody CreateOrUpdateAd ad,
                                     @RequestBody MultipartFile image) {
        return new CreateOrUpdateAd();
    }

    @GetMapping("/{id}")
    public ExtendedAd getAd(@PathVariable int id) {
        return new ExtendedAd();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAd(@PathVariable int id) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public Ad updateAd(@PathVariable int id, @RequestBody CreateOrUpdateAd newAd) {
        return new Ad();
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public byte[] updateAdImage(@PathVariable int id, @RequestBody MultipartFile image) {
        return new byte[0];
    }

    @GetMapping("/me")
    public Ads getAdsOfUser() {
        return new Ads();
    }
}
