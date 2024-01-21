package com.tfunsal.eventhub.services.impl;

import com.tfunsal.eventhub.dtos.JwtAuthenticationResponseDto;
import com.tfunsal.eventhub.dtos.RefreshTokenRequestDto;
import com.tfunsal.eventhub.dtos.SignInRequestDto;
import com.tfunsal.eventhub.dtos.SignUpRequestDto;
import com.tfunsal.eventhub.models.Role;
import com.tfunsal.eventhub.models.User;
import com.tfunsal.eventhub.repository.UserRepository;
import com.tfunsal.eventhub.services.AuthService;
import com.tfunsal.eventhub.services.JwtService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;


    public User signUp(SignUpRequestDto signUpRequestDto) {

        User user = new User();
        user.setEmail(signUpRequestDto.getEmail());
        user.setName(signUpRequestDto.getName());
        user.setLastName(signUpRequestDto.getLastName() );
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));

        return userRepository.save(user);
    }

    public JwtAuthenticationResponseDto signIn(SignInRequestDto signInRequestDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequestDto.getEmail(), signInRequestDto.getPassword()));

        var user = userRepository.findByEmail(signInRequestDto.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password!"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponseDto jwtAuthenticationResponseDto = new JwtAuthenticationResponseDto();
        jwtAuthenticationResponseDto.setToken(jwt);
        jwtAuthenticationResponseDto.setRefreshToken(refreshToken);

        return jwtAuthenticationResponseDto;
    }

    public JwtAuthenticationResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        String userEmail = jwtService.extractUserName(refreshTokenRequestDto.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        if (jwtService.isTokenValid(refreshTokenRequestDto.getToken(), user)) {
            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponseDto jwtAuthenticationResponse = new JwtAuthenticationResponseDto();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequestDto.getToken());

            return jwtAuthenticationResponse;
        }
        return null;

    }

    @PostConstruct
    public void createAdminAccount() {

        User adminAccount = userRepository.findByRole(Role.ADMIN);
        if (adminAccount == null) {

            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("John");
            user.setLastName("Doe");
            user.setRole(Role.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));

            userRepository.save(user);
        }
    }
}
