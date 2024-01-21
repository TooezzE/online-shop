package ru.skypro.homework.service.interfaces;

import ru.skypro.homework.dto.RegisterDTO;

public interface AuthService {
    boolean login(String userName, String password);

    boolean register(RegisterDTO register);
}
