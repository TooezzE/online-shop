package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.CustomUserDetailsService;

@Service
public class AuthServiceImpl implements AuthService {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder encoder;
    private final UserMapper mapper;
    private UserRepository repository;

    public AuthServiceImpl(CustomUserDetailsService userDetailsService,
                           PasswordEncoder passwordEncoder, UserMapper mapper, UserRepository repository) {
        this.userDetailsService = userDetailsService;
        this.encoder = passwordEncoder;
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public boolean login(String userName, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
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

    @Override
    public boolean register(Register register) {
        User user = mapper.registerToUser(register);
        repository.save(user);
        return true;
    }

}
