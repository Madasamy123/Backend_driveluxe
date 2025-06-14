package com.madasamy.driveluxe.controller;

import com.madasamy.driveluxe.starter.util.JwtUtil;
import com.madasamy.driveluxe.dto.RegisterRequest;
import com.madasamy.driveluxe.model.User;
import com.madasamy.driveluxe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        if (userService.findByEmail(registerRequest.getUseremail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists!");
        }

        //  Check if the email is for admin; if yes, assign "ADMIN", otherwise "USER"
        String role = "USER";
        if (registerRequest.getUseremail().equalsIgnoreCase("admin@gmail.com")) {
            role = "ADMIN";
        }

        String hashedPassword = userService.encodePassword(registerRequest.getPassword());
        User user = new User(registerRequest.getUseremail(), hashedPassword, role);
        userService.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }




    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> userMap) {
        String useremail = userMap.get("useremail");
        String password = userMap.get("password");

        Optional<User> userOptional = userService.findByEmail(useremail);

        Map<String, String> response = new HashMap<>();

        if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
            String token = jwtUtil.generateToken(useremail);
            String role = userOptional.get().getRole();  // Fetch role from user
            response.put("token", token);
            response.put("role", role);  // Include role in the response
        } else {
            response.put("error", "Invalid email or password");
        }
        return response;
    }





}
