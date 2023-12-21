package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.interfaces.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final CustomUserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;
    private final UserRepository userRepository;


    public AuthServiceImpl(CustomUserDetailsManager manager, PasswordEncoder encoder,
                           UserMapper userMapper, UserRepository userRepository) {
        this.manager = manager;
        this.encoder = encoder;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
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
        User user = userMapper.registerToUser(register);
        user.setRole(register.getRole());
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }


}
