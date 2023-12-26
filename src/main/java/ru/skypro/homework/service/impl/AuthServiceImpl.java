package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.exception.InvalidPasswordException;
import ru.skypro.homework.manager.CustomUserDetailsService;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final CustomUserDetailsService manager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthServiceImpl(CustomUserDetailsService manager, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.manager = manager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }
    /**
     * Логика аутентификации пользователя.
     * Метод использует {@link CustomUserDetailsService#loadUserByUsername(String)}
     * {@link PasswordEncoder#matches(CharSequence, String)}
     * @param userName - логин пользователя
     * @param password - пароль пользователя
     * @return true, если аутентификация прошла успешно, иначе - false.
     */
    //Метод аутентификации пользователя.
    @Override
    public boolean login(String userName, String password) {
        log.info("Attempting login for user: {}", userName);
        UserDetails userDetails = manager.loadUserByUsername(userName);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }
        return true;
    }
    /**
     * Логика регистрации нового пользователя.
     * Метод использует {@link UserRepository#findByEmail(String)}
     * {@link PasswordEncoder#encode(CharSequence)}
     * {@link UserMapper#registerDTOToUser(RegisterDTO)}
     * @param register - информация о пользователе, который необходимо зарегистрировать.
     *                 Модель класса {@link RegisterDTO}
     * @return true, если регистрация прошла успешно, иначе - false.
     */

    // Метод регестрации нового  пользователя.
    @Override
    public boolean register(RegisterDTO register) {
        log.info("Attempting registration");
        if (userRepository.findByEmail(register.getUsername()) != null) {
            return false;
        }
        register.setPassword(passwordEncoder.encode(register.getPassword()));
        userRepository.save(UserMapper.INSTANCE.registerDTOToUser(register));
        return true;
    }

}
