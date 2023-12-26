package ru.skypro.homework.service.impl;
import org.springframework.data.jpa.repository.JpaRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.InvalidPasswordException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ImageService imageService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.imageService = imageService;
        this.passwordEncoder = passwordEncoder;
    }
    /**
     * Извлекает текущего аутентифицированного пользователя.
     * Метод использует {@link UserRepository#findByEmail(String)}
     * {@link UserMapper#toUserDTO(User)}
     * @return - объект UserDTO, представляющий текущего пользователя.
     */
    //Метод извлекает текущего аутентифицированного пользователя.
    @Override
    public UserDTO getCurrentUser() {
        log.info("Current user received");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName());
        return UserMapper.INSTANCE.toUserDTO(user);
    }
    /**
     * Изменение данных пользователя
     * Метод использует {@link UserRepository#findByEmail(String)}
     * {@link UserMapper#updateUserDTOToUser(UpdateUserDTO, User)}
     * {@link UserMapper#toUserDTO(User)}
     * @param updateUserDTO - DTO модель класса {@link UpdateUserDTO}
     * @return - пользователя с измененными данными
     */

    //Метод изменение данных у пользователя
    @Override
    public UserDTO updateUser(UpdateUserDTO updateUserDTO) {
        log.info("Update user completed");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName());
        UserMapper.INSTANCE.updateUserDTOToUser(updateUserDTO, user);
        return UserMapper.INSTANCE.toUserDTO(userRepository.save(user));
    }
    /**
   * Изменение пароля пользователя
     * метод использует {@link UserRepository#findByEmail(String)}
     * {@link PasswordEncoder#matches(CharSequence, String)}
     * {@link PasswordEncoder#encode(CharSequence)}
     * {@link JpaRepository#save(Object)}
     * @param newPasswordDTO - новый пароль пользователя
     */
    //  Метод изменения пароля у пользователя
    @Override
    public Void setPassword(NewPasswordDTO newPasswordDTO) {
        log.info("Change password");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());

        if (user == null) {
            // Обработка случая, если пользователя не существует
            throw new UserNotFoundException("User not found");
        }

        // Проверка, что старый пароль совпадает
        if (!passwordEncoder.matches(newPasswordDTO.getCurrentPassword(), user.getPassword())) {
            // Обработка случая, если старый пароль не совпадает
            throw new InvalidPasswordException("Incorrect old password");
        }

        // Обновление пароля
        String hashedPassword = passwordEncoder.encode(newPasswordDTO.getNewPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return null;
    }
    /**
     * Изменение аватарки пользователя
     * Метод использует {@link UserRepository#findByEmail(String)}
     * {@link ImageService#addImage(MultipartFile)}
     * {@link JpaRepository#save(Object)}
     * {@link ImageService#deleteImage(Integer)}
     * @param image - новая аватарка пользователя
     * @param userName - логин пользователя
     */
    // Метод обновления  аватарки у пользователя
    @Override
    @Transactional
    public void updateUserImage(MultipartFile image, String userName) {
        log.info("Avatar update compleated");
        User user = userRepository.findByEmail(userName);
        if (user.getImage() == null) {
            user.setImage(imageService.addImage(image));
            userRepository.save(user);
            return;
        }
        Integer imageId = user.getImage().getId();
        user.setImage(imageService.addImage(image));
        imageService.deleteImage(imageId);
        userRepository.save(user);
    }
}
