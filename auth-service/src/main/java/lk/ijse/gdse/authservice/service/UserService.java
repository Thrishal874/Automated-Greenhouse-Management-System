package lk.ijse.gdse.authservice.service;

import lk.ijse.gdse.authservice.dto.UserDTO;
import lk.ijse.gdse.authservice.entity.User;

import java.util.Map;

public interface UserService {
    void register(UserDTO userDTO);

    Map<String, String> login(UserDTO userDTO);

    String refreshAccessToken(String refreshToken);
}
