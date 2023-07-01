package com.technomentoring.mentoringapi.dto;

import lombok.Data;

@Data
public class AuthRespuestaDTO {
    private String accessToken;
    private String tokenType= "Bearer ";

    public AuthRespuestaDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
