package ru.skypro.homework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.CustomUserDetailsService;

@Service
public class AuthServiceImpl implements AuthService {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder encoder;
    @Autowired
    private UserRepository repository;

    public AuthServiceImpl(CustomUserDetailsService userDetailsService,
                           PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.encoder = passwordEncoder;
    }

    @Override
    public boolean login(String userName, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(Register register) {
        User user = new User(0, register.getUsername(),
                encoder.encode(register.getPassword()), register.getFirstName(), register.getLastName(),
                register.getPhone(), register.getRole(), "hello");
        repository.save(user);
        return true;
    }

}
