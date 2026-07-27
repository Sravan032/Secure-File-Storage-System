package com.sravan.Secure.File.Storage.Service;

import com.sravan.Secure.File.Storage.Security.JwtService;
import com.sravan.Secure.File.Storage.model.User;
import com.sravan.Secure.File.Storage.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void registerUser(String username, String rawPassword) {

        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        String hashedPassword = passwordEncoder.encode(rawPassword);

        User user = new User(username, hashedPassword, "USER");

        userRepository.save(user);
    }

    public String loginUser(String username, String rawPassword) {

        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            return null;
        }

        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            return jwtService.generateToken(username);
        }

        return null;
    }
}