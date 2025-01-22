package com.rtecnico.afiliaciones.controller;

import com.rtecnico.afiliaciones.dto.request.LoginDTO;
import com.rtecnico.afiliaciones.dto.request.TokenRequest;
import com.rtecnico.afiliaciones.dto.response.AuthResponseDTO;
import com.rtecnico.afiliaciones.dto.response.ResponseDTO;
import com.rtecnico.afiliaciones.service.AuthService;
import com.rtecnico.afiliaciones.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<AuthResponseDTO>> login(@RequestBody LoginDTO loginRequest) {
        ResponseDTO<AuthResponseDTO> response = authService.login(loginRequest);

        if (response.getStatus() == 401) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }


    @PostMapping("/validate-token")
    public ResponseEntity<Boolean> validateToken(@RequestBody TokenRequest tokenRequest) {
        String token = tokenRequest.getToken();

        if (jwtService.validateToken(token)) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
    }

}