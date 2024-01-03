package com.finture.mymicroservice;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
public class MyController {
    @GetMapping("/secure-endpoint")
    public String secureEndpoint(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, HttpServletRequest request, HttpServletResponse response) {

        if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
            String credentials = new String(Base64.getDecoder().decode(authorizationHeader), StandardCharsets.UTF_8);
            String[] parts = credentials.split(":");
            String clientUsername = parts.length >= 1 ? parts[0] : "";
            String clientPassword = parts.length >= 2 ? parts[1] : "";

            if (clientUsername.equals("admin") && clientPassword.equals("password")) {

                return "Pomyślnie uwierzytelniono!";
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return "Błąd uwierzytelniania!";
    }

    private void confirmText(){
        System.out.println("To jest pozytywne uruchomienie metody");
    }
}



