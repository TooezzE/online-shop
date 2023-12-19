package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.service.UserService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/set_password")
    public ResponseEntity<?> updatePassword(@RequestBody NewPassword newPassword, Authentication auth) {
        if(service.updatePassword(auth.getName(), newPassword)) {
            return ResponseEntity.ok().build();
        } else if (!auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUserInfo(Authentication auth) {
        UserDTO userDTO = service.getUserInfo(auth.getName());
        if(userDTO != null) {
            return ResponseEntity.ok().body(userDTO);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PatchMapping("/me")
    public ResponseEntity<UpdateUser> updateUserInfo(@RequestBody UpdateUser updateUser, Authentication auth) {
        if(service.updateUserInfo(auth.getName(), updateUser) != null) {
            return ResponseEntity.ok().body(updateUser);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserImage(@RequestBody MultipartFile image, Authentication auth) {
        return ResponseEntity.ok().build();
    }
}
