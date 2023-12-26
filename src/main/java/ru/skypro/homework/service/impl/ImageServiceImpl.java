package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.exception.ForbiddenException;
import ru.skypro.homework.exception.ImageNotFoundException;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    //Метод получения изображения по ID
    @Override
    public ResponseEntity<byte[]> getImage(Integer id) {
        log.info("Getting image by ID: {}", id);
        Image image = imageRepository.findById(id).orElseThrow(ImageNotFoundException::new);
        byte[] imageBytes = image.getData();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(image.getMediaType()));
        headers.setContentLength(imageBytes.length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(imageBytes);
    }

    //Метод добавления изображения
    @Override
    public Image addImage(MultipartFile image) {
        log.info("Create and update image");
        Image newImage = new Image();
        try {
            newImage.setData(image.getBytes());
            newImage.setMediaType(image.getContentType());
            newImage.setFileSize(image.getSize());
        } catch (IOException e) {
            throw new ForbiddenException(e);

        }
        imageRepository.save(newImage);
        return newImage;
    }

    //Метод удаления изображения
    @Override
    public void deleteImage(Integer imageId) {
        log.info("Delete image");
        imageRepository.deleteById(imageId);
    }
}
