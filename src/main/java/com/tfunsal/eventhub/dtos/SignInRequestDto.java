package com.tfunsal.eventhub.dtos;

import lombok.Data;

@Data
public class SignInRequestDto {

    private String email;

    private String password;
}
