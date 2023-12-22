package ru.skypro.homework.mappers;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.entity.User;

@Service
public class UserMapper {

    public Login userToLogin(User user) {
        Login login = new Login();
        login.setUsername(user.getEmail());
        login.setPassword(user.getPassword());

        return login;
    }

    public Register userToRegister(User user) {
        Register register = new Register();
        register.setUsername(user.getEmail());
        register.setPassword(user.getPassword());
        register.setFirstName(user.getFirstName());
        register.setLastName(user.getLastName());
        register.setPhone(user.getPhone());
        register.setRole(user.getRole());

        return register;
    }

    public UpdateUser userToUpdateUser(User user) {
        UpdateUser updateUser = new UpdateUser();
        updateUser.setFirstName(user.getFirstName());
        updateUser.setLastName(user.getLastName());
        updateUser.setPhone(user.getPhone());

        return updateUser;
    }

    public UserDTO userToUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setImage("/images/get/" + user.getImage().getId());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());

        return dto;
    }

    public User userDTOToUser(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        user.setRole(dto.getRole());

        return user;
    }

    public User updateUserToUser(UpdateUser dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());

        return user;
    }

    public User registerToUser(Register register) {
        User user = new User();
        user.setEmail(register.getUsername());
        user.setPassword(register.getPassword());
        user.setFirstName(register.getFirstName());
        user.setLastName(register.getLastName());
        user.setPhone(register.getPhone());

        return user;
    }

    public User loginToUser(Login login) {
        User user = new User();
        user.setEmail(login.getUsername());
        user.setPassword(login.getPassword());

        return user;
    }
}
