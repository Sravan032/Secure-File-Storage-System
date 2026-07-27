package com.sravan.Secure.File.Storage.Controller;

import com.sravan.Secure.File.Storage.Security.JwtService;
import com.sravan.Secure.File.Storage.Service.UserService;
import com.sravan.Secure.File.Storage.dto.LoginRequest;
import com.sravan.Secure.File.Storage.dto.RegisterRequest;
import com.sravan.Secure.File.Storage.model.User;
import com.sravan.Secure.File.Storage.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthController(UserService userService,
                          UserRepository userRepository,
                          JwtService jwtService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        userService.registerUser(
                request.getUsername(),
                request.getPassword()
        );

        return "User registration successful";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        String token = userService.loginUser(
                request.getUsername(),
                request.getPassword()
        );

        if (token != null) {
            return token;
        }

        return "Incorrect Username or Password!";
    }

    @GetMapping("/test")
    public String test(@RequestHeader("Authorization") String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return "Missing or Invalid Token!";
        }

        String token = authHeader.substring(7);

        String username = jwtService.extractUsername(token);

        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            return "Invalid Token!";
        }

        return "Hello " + username + ", protected endpoint accessed.";
    }
}