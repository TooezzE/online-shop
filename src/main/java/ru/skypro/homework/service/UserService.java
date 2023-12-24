package ru.skypro.homework.service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ImageService imageService;
    private final PasswordEncoder encoder;
    private final UserMapper mapper;

    public UserService(UserRepository userRepository, ImageService imageService, PasswordEncoder encoder, UserMapper mapper) {
        this.userRepository = userRepository;
        this.imageService = imageService;
        this.encoder = encoder;
        this.mapper = mapper;
    }

    /**
     * Изменение пароля пользователя
     * метод использует {@link UserRepository#findByEmail(String)}
     * {@link PasswordEncoder#matches(CharSequence, String)}
     * {@link PasswordEncoder#encode(CharSequence)}
     * {@link JpaRepository#save(Object)}
     * @param newPassword - новый пароль пользователя
     */
    public boolean updatePassword(String username, NewPassword newPassword) {
        User user = userRepository.findByEmail(username);
        if(encoder.matches(newPassword.getCurrentPassword(), user.getPassword())) {
            user.setPassword(encoder.encode(newPassword.getNewPassword()));
            userRepository.save(user);
            return true;
        }
        return false;
    }
    /**
     * Извлекает текущего аутентифицированного пользователя.
     * Метод использует {@link UserRepository#findByEmail(String)}
     * {@link UserMapper#userToUserDTO(User)}
     * @return - объект UserDTO, представляющий текущего пользователя.
     */
    @Transactional
    public UserDTO getUserInfo(String username) {
        User user = userRepository.findByEmail(username);
        return mapper.userToUserDTO(user);
    }
    /**
     * Изменение данных пользователя
     * Метод использует {@link UserRepository#findByEmail(String)}
     * @param updateUser - DTO модель класса {@link UpdateUser}
     * @return - пользователя с измененными данными
     */

    public UpdateUser updateUserInfo(String username, UpdateUser updateUser) {
        User user = userRepository.findByEmail(username);
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        user.setPhone(updateUser.getPhone());
        userRepository.save(user);
        return updateUser;
    }
    /**
     * Изменение аватарки пользователя
     * Метод использует {@link UserRepository#findByEmail(String)}
     * {@link ImageService#addImage(MultipartFile)}
     * {@link JpaRepository#save(Object)}
     * {@link ImageService#deleteImage(Integer)}
     * @param file - новая аватарка пользователя
     * @param username - логин пользователя
     */
    public void updateUserImage(String username, MultipartFile file) {
        User user = userRepository.findByEmail(username);
        if (user.getImage() == null) {
            user.setImage(imageService.addImage(file));
            userRepository.save(user);
            return;
        }
        Integer imageId = user.getImage().getId();
        user.setImage(imageService.addImage(file));
        imageService.deleteImage(imageId);
        userRepository.save(user);
    }
}

