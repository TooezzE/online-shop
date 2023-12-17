package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.service.interfaces.AuthService;
import ru.skypro.homework.service.CustomUserDetailsManager;

@Service
public class AuthServiceImpl implements AuthService {

    private CustomUserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;


    public AuthServiceImpl(PasswordEncoder encoder,
                           UserMapper userMapper) {
        this.encoder = encoder;
        this.userMapper = userMapper;
    }

    @Override
    public boolean login(String userName, String password) {
        if (!manager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(Register register) {
        if (manager.userExists(register.getUsername())) {
            return false;
        }
        manager.createUser(userMapper.registerToUser(register));
        return true;
    }

}
