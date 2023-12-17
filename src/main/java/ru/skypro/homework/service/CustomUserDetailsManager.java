package ru.skypro.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;

@Component
public class CustomUserDetailsManager implements UserDetailsManager {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createUser(UserDetails user) {
        User newUser = (User) user;
        userRepository.save(newUser);
    }

    @Override
    public void updateUser(UserDetails user) {
        // released by UserService
    }

    @Override
    public void deleteUser(String username) {
        // released by UserService
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        // released by UserService
    }

    @Override
    public boolean userExists(String username) {
        User user = userRepository.findByEmail(username);
        return user != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }
}
