package com.skillhire.backend.service;

import com.skillhire.backend.dto.auth.AuthResponse;
import com.skillhire.backend.dto.auth.LoginRequest;
import com.skillhire.backend.dto.auth.RegisterRequest;
import com.skillhire.backend.entity.User;
import com.skillhire.backend.exception.BadRequestException;
import com.skillhire.backend.exception.InvalidCredentialsException;
import com.skillhire.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Mapper mapper;

    @Transactional
    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.email())) {
            throw new BadRequestException("An account with this email already exists.");
        }
        User user = User.builder()
                .name(req.name())
                .email(req.email())
                .password(passwordEncoder.encode(req.password()))
                .role(req.role())
                .build();
        return mapper.toAuthResponse(userRepository.save(user));
    }

    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.email())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password."));
        if (!passwordEncoder.matches(req.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password.");
        }
        return mapper.toAuthResponse(user);
    }
}
