package ru.skypro.homework.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.config.WebSecurityConfig;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.entity.User;

@Service
public class UserMapper {
    @Autowired
    UserDetailsManager manager;
    @Autowired
    WebSecurityConfig webSecurityConfig;


    public void changePassword(NewPassword newPassword) {
        InMemoryUserDetailsManager user = webSecurityConfig.userDetailsService(webSecurityConfig.passwordEncoder());
         user.changePassword(newPassword.getCurrentPassword(), newPassword.getNewPassword());
    }


    public User updateUser(UpdateUser updateUser) {
        User user = new User();
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        user.setPhone(updateUser.getPhone());
        return user;
    }

}
