package lk.ijse.gdse.authservice.controller;

import lk.ijse.gdse.authservice.dto.ApiResponse;
import lk.ijse.gdse.authservice.dto.UserDTO;
import lk.ijse.gdse.authservice.service.UserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping({"/auth"})
@CrossOrigin({"*"})
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody UserDTO userDTO) {

        userService.register(userDTO);

        return ResponseEntity.ok(
                new ApiResponse(
                        200,
                        "User registered successfully",
                        userDTO
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@RequestBody UserDTO userDTO) {
        Map<String, String> tokens = userService.login(userDTO);
        return ResponseEntity.ok(
                new ApiResponse(200, "Login successful", tokens)
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        String newAccessToken = userService.refreshAccessToken(refreshToken);

        return ResponseEntity.ok(
                new ApiResponse(200, "Token refreshed successfully", newAccessToken)
        );
    }

}
