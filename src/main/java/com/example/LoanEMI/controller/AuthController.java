package com.example.LoanEMI.controller;

import com.example.LoanEMI.dto.*;
import com.example.LoanEMI.jwt.JwtUtil;
import com.example.LoanEMI.model.User;
import com.example.LoanEMI.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        userService.save(user);
        return "Signup successful";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            return "Invalid username or password";
        }

        return jwtUtil.generateToken(request.getUsername());

    }
}
