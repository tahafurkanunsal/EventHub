package com.tfunsal.eventhub.dtos;

import lombok.Data;

@Data
public class SignUpRequestDto {

    private String name;
    private String lastName;
    private String email;
    private String password;
}
