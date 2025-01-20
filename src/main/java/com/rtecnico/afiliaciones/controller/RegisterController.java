package com.rtecnico.afiliaciones.controller;

import com.rtecnico.afiliaciones.dto.request.RegisterDTO;
import com.rtecnico.afiliaciones.dto.response.RegisterResponseDTO;
import com.rtecnico.afiliaciones.dto.response.ResponseDTO;
import com.rtecnico.afiliaciones.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping
    public Mono<ResponseEntity<ResponseDTO<RegisterResponseDTO>>> registerAgent(
            @RequestBody RegisterDTO registerDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return Mono.just(ResponseEntity.badRequest().body(
                    ResponseDTO.<RegisterResponseDTO>builder()
                            .status(400)
                            .message("Validation failed: " + bindingResult.getAllErrors().stream()
                                    .map(ObjectError::getDefaultMessage)
                                    .collect(Collectors.joining(", ")))
                            .data(null)
                            .build()
            ));
        }

        return registerService.registerAgent(registerDTO)
                .map(response -> ResponseEntity.ok(
                        ResponseDTO.<RegisterResponseDTO>builder()
                                .status(200)
                                .message("Agente registrado correctamente y correo enviado")
                                .data(response)
                                .build()
                ))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(
                        ResponseDTO.<RegisterResponseDTO>builder()
                                .status(400)
                                .message("Error al registrar el agente: " + e.getMessage())
                                .data(null)
                                .build()
                )));
    }
}
