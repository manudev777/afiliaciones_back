package com.rtecnico.afiliaciones.dto.request;

import lombok.Data;

@Data
public class EmailDTO {
    private String to;
    private String subject;
    private String body;
}

