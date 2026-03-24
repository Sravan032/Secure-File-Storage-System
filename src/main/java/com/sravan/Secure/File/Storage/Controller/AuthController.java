package com.sravan.Secure.File.Storage.Controller;

import com.sravan.Secure.File.Storage.Service.UserService;
import com.sravan.Secure.File.Storage.dto.LoginRequest;
import com.sravan.Secure.File.Storage.dto.RegisterRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService){
        this.userService=userService;
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
}
