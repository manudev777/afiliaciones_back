package com.rtecnico.afiliaciones.service;

import com.rtecnico.afiliaciones.dto.request.RegisterDTO;
import com.rtecnico.afiliaciones.dto.response.RegisterResponseDTO;
import com.rtecnico.afiliaciones.model.Agent;
import com.rtecnico.afiliaciones.model.Users;
import com.rtecnico.afiliaciones.repository.AgentRepository;
import com.rtecnico.afiliaciones.repository.UserRepository;
import com.rtecnico.afiliaciones.utils.PasswordEncryption;
import com.rtecnico.afiliaciones.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class RegisterService {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Value("${url.path.frontend}")
    private String urlPath;

    public Mono<RegisterResponseDTO> registerAgent(RegisterDTO registerDTO) {
        Agent agent = buildAgentFromDTO(registerDTO);

        return Mono.fromCallable(() -> agentRepository.save(agent))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(savedAgent -> {

                    String username = generateRandomUser(savedAgent);
                    String password = Double.toString(Math.random()).substring(2, 10);

                    String emailContent = buildEmailContent(username,password);
                    String encryptedPassword = PasswordEncryption.encryptPassword(password);

                    Mono<Void> saveUserMono = Mono.fromRunnable(() -> {
                        userRepository.save(Users.builder()
                                .username(username)
                                .password(encryptedPassword)
                                .agent(savedAgent)
                                .build());
                    }).subscribeOn(Schedulers.parallel()).then();

                    Mono<Void> sendEmailMono = Mono.fromRunnable(() -> emailService.sendEmail(
                            savedAgent.getEmail(),
                            "Modulo de Afiliaciones",
                            emailContent
                    )).subscribeOn(Schedulers.boundedElastic()).then();

                    return saveUserMono.then(sendEmailMono).thenReturn(savedAgent);
                })
                .map(this::buildRegisterResponse);
    }

    private String buildEmailContent(String username, String password) {
        return String.format("<html><body>"
                + "<p>Estimado cliente,</p>"
                + "<p>Gracias por tu interés en formar parte de nuestra comunidad.</p>"
                + "<p>Hemos creado tu Usuario y Password para que puedas iniciar tu afiliación como uno de nuestros agentes. "
                + "Ingresa al siguiente link que te enviamos usando las siguientes credenciales:</p>"
                + "<p><a href=\"%s\">%s</a></p>"
                + "<p>Usuario: %s<br>Password: %s</p>"
                + "<p>Asimismo, te enviamos la lista de requisitos, los cuales te solicitaremos en los próximos pasos del proceso de afiliación:</p>"
                + "<ul>"
                + "<li><b>Copia de DNI:</b> Copia simple del DNI del Titular del comercio</li>"
                + "<li><b>Licencia de funcionamiento:</b> Copia legible de la licencia/resolución municipal definitiva...</li>"
                + "<li><b>Ficha RUC:</b> Copia simple del estado del contribuyente, debe ser ACTIVO...</li>"
                + "<li><b>Vigencia de poder (Persona Jurídica):</b> Sólo para personas jurídicas...</li>"
                + "<li><b>Voucher de depósito:</b> Se requiere un saldo mínimo de S/2500...</li>"
                + "</ul>"
                + "<p>Gracias,</p>"
                + "<p><b>Tu equipo de soporte</b></p>"
                + "</body></html>",urlPath,urlPath,username,password);
    }

    private Agent buildAgentFromDTO(RegisterDTO dto) {
        return Agent.builder()
                .name(dto.getName())
                .lastname(dto.getLastname())
                .lastnameSecond(dto.getLastnameSecond())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .ruc(dto.getRuc())
                .department(dto.getDepartment())
                .province(dto.getProvince())
                .district(dto.getDistrict())
                .build();
    }

    private RegisterResponseDTO buildRegisterResponse(Agent saved) {
        return RegisterResponseDTO.builder()
                .name(saved.getName())
                .email(saved.getEmail())
                .phone(saved.getPhone())
                .department(saved.getDepartment())
                .province(saved.getProvince())
                .district(saved.getDistrict())
                .build();
    }

    private String generateRandomUser(Agent agent){
        String firstLetterName = String.valueOf(agent.getName().toUpperCase().charAt(0));
        String lastName = Utils.removeAccents(agent.getLastname()).toUpperCase();
        String randomNumber = Double.toString(Math.random()).substring(1, 5);

        return firstLetterName + lastName + randomNumber;
    }
}
