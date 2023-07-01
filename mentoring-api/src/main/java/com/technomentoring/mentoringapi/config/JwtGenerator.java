package com.technomentoring.mentoringapi.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtGenerator {
    ///metodo para crear un token por autenticacion
    public String generarToken(Authentication authentication){
        String username = authentication.getName();
        Date tiempoActual = new Date();
        Date expiracionToken = new Date(tiempoActual.getTime() + ConstantsSecurity.JWT_EXPIRATION_TOKEN);

        ///Linea para generar el token
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiracionToken)
                .signWith(SignatureAlgorithm.HS512,ConstantsSecurity.JWT_FIRMA)
                .compact();
        return token;
    }

    /////metodo para extraer un username apartir de un token

    public String obtenerUsernameDeJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(ConstantsSecurity.JWT_FIRMA)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    /////validar token

    public boolean validarToken(String token){
        try {
            Jwts.parser().setSigningKey(ConstantsSecurity.JWT_FIRMA).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            throw new AuthenticationCredentialsNotFoundException("Jwt a expirado o esta incorrecto");
        }
    }
}
