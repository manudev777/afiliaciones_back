package com.rtecnico.afiliaciones.service;

import com.rtecnico.afiliaciones.dto.request.LoginDTO;
import com.rtecnico.afiliaciones.dto.response.AuthResponseDTO;
import com.rtecnico.afiliaciones.dto.response.ResponseDTO;
import com.rtecnico.afiliaciones.model.Agent;
import com.rtecnico.afiliaciones.model.Users;
import com.rtecnico.afiliaciones.repository.AgentRepository;
import com.rtecnico.afiliaciones.repository.UserRepository;
import com.rtecnico.afiliaciones.utils.PasswordEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AgentRepository agentRepository;

    public ResponseDTO<AuthResponseDTO> login(LoginDTO loginRequest) {
        Users user = userRepository.findByUsername(loginRequest.getUsername()).orElse(null);
        if (user != null && PasswordEncryption.checkPassword(loginRequest.getPassword(), user.getPassword())) {
            String token = jwtService.generateToken(user.getUsername());

            return new ResponseDTO<>("Login successful", 200, AuthResponseDTO.builder().
                    username(user.getUsername())
                    .token(token)
                    .build());
        } else {
            // Devolver respuesta de error en caso de credenciales inválidas
            return new ResponseDTO<>("Invalid username or password", 401, null);
        }
    }
}
