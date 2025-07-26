package com.example.LoanEMI.service;

import com.example.LoanEMI.model.User;
import com.example.LoanEMI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }
}
