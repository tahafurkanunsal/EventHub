package com.tfunsal.eventhub.services;

import com.tfunsal.eventhub.dtos.JwtAuthenticationResponseDto;
import com.tfunsal.eventhub.dtos.RefreshTokenRequestDto;
import com.tfunsal.eventhub.dtos.SignInRequestDto;
import com.tfunsal.eventhub.dtos.SignUpRequestDto;
import com.tfunsal.eventhub.models.User;

public interface AuthService {

    User signUp(SignUpRequestDto signUpRequestDto);

    JwtAuthenticationResponseDto signIn(SignInRequestDto signInRequestDto);

    JwtAuthenticationResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto);
}
