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

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void RegisterUser(String username, String rawpassword) {
        String hashedPassword = passwordEncoder.encode(rawpassword);
        User user = new User(username,hashedPassword,"User");
        userRepository.save(user);
    }
// Old
//    public boolean LoginUser(String username, String rawpassword){
//        User user = userRepository.findByUsername(username).orElse(null);
//        if(user==null) return false;
//        return passwordEncoder.matches(rawpassword,user.getPassword());
//    }

    // New
    public String LoginUser(String username, String rawpassword){
        User user = userRepository.findByUsername(username).orElse(null);
        if(user == null) return null;

        if(passwordEncoder.matches(rawpassword,user.getPassword())){
            return jwtService.GenerateToken(username);
        }
        return null;
    }
}
