package ru.skypro.homework.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDTO;

@Controller
@RequestMapping("/users")
public class UserController {

    @PostMapping("/set_password")
    public ResponseEntity<?> updatePassword(@RequestBody NewPassword newPassword) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public UserDTO getUserInfo() {
        return new UserDTO();
    }

    @PatchMapping("/me")
    public UpdateUser updateUserInfo(@RequestBody UpdateUser updateUser) {
        return new UpdateUser();
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserImage(@RequestBody MultipartFile image) {
        return ResponseEntity.ok().build();
    }
}
