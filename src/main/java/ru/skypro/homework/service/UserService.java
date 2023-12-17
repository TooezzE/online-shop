package ru.skypro.homework.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final UserMapper mapper;

    public UserService(UserRepository repository, PasswordEncoder encoder, UserMapper mapper) {
        this.repository = repository;
        this.encoder = encoder;
        this.mapper = mapper;
    }


    public boolean updatePassword(String username, NewPassword newPassword) {
        User user = repository.findByEmail(username);
        if(encoder.matches(user.getPassword(), newPassword.getCurrentPassword())) {
            user.setPassword(encoder.encode(newPassword.getNewPassword()));
            repository.save(user);
            return true;
        }
        return false;
    }

    public UserDTO getUserInfo(String username) {
        User user = repository.findByEmail(username);
        return mapper.userToUserDTO(user);
    }

    public UpdateUser updateUserInfo(String username, UpdateUser updateUser) {
        User user = repository.findByEmail(username);
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        user.setPhone(updateUser.getPhone());
        repository.save(user);
        return updateUser;
    }
}

