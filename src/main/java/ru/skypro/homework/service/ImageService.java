package ru.skypro.homework.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.exceptions.ImageNotFoundException;
import ru.skypro.homework.repository.ImageRepository;

import java.io.IOException;

@Service
public class ImageService {

    private final ImageRepository repository;

    public ImageService(ImageRepository repository) {
        this.repository = repository;
    }

    /**
     * Получение изображения по ID
     * Метод использует {@link JpaRepository#findById(Object)}
     *
     * @param id - id изображения
     */
    public Image getImage(Integer id) {
        return repository.findById(id).orElseThrow(ImageNotFoundException::new);
    }

    /**
     * Добавление изображения в репозиторий
     * Метод использует {@link JpaRepository#save(Object)}
     *
     * @param image - изображение
     * @return добавленное изображение
     */
    public Image addImage(MultipartFile image) {
        Image newImage = new Image();
        try {
            newImage.setBytes(image.getBytes());
            newImage.setContentType(image.getContentType());
            newImage.setFileSize(image.getSize());
            repository.save(newImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newImage;
    }

    /**
     * Удаление изображения из репозитория
     * Метод использует {@link JpaRepository#deleteById(Object)}
     * @param imageId - id изображения
     */
    public void deleteImage(Integer imageId) {
        repository.deleteById(imageId);
    }
}
