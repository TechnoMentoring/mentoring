package com.technomentoring.mentoringapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TokenRequestDTO {
    private String accessToken;
    private String tokenType;
}
