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

    public AuthController(UserService userService, UserRepository userRepository){
        this.userService=userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public String Register(@RequestBody RegisterRequest request){
        userService.RegisterUser(
                request.getUsername(),
                request.getPassword()
        );
        return "User registration is successful";
    }
    @PostMapping("/login")
    public String Login(@RequestBody LoginRequest request){
//        boolean success = userService.LoginUser(
//                request.getUsername(),
//                request.getPassword()
//        );
        String token = userService.LoginUser(
                request.getUsername(),
                request.getPassword()
        );
        if(token != null){
//            return "Login Successfull...";
            return token;
        }else{
            return "Incorrect Username or Password!";
        }
    }


    @GetMapping("/test")
//    public String test(){
//        return "Protected endpoint accessed ..";
//    }
    public String test(@RequestHeader("Authorization") String authHeader){
        if(authHeader == null || !authHeader.startsWith("Bearer")) return "missing or Invalid token!";
        String token = authHeader.substring(7);
        String username = JwtService.extractUsername(token);
        User user = userRepository.findByUsername(username).orElse(null);
        if(user==null)
            return "Invalid Token!";

        return "Hello "+username +", protected endpoint accessed..";
    }
}
