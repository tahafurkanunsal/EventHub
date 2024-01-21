package com.tfunsal.eventhub.controllers;

import com.tfunsal.eventhub.dtos.JwtAuthenticationResponseDto;
import com.tfunsal.eventhub.dtos.RefreshTokenRequestDto;
import com.tfunsal.eventhub.dtos.SignInRequestDto;
import com.tfunsal.eventhub.dtos.SignUpRequestDto;
import com.tfunsal.eventhub.models.User;
import com.tfunsal.eventhub.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        return ResponseEntity.ok(authService.signUp(signUpRequestDto));

    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponseDto> signIn(@RequestBody SignInRequestDto signInRequestDto) {
        return ResponseEntity.ok(authService.signIn(signInRequestDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequestDto));
    }
}
