package com.rtecnico.afiliaciones.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDTO {
    private String name;
    private String email;
    private String phone;
    private String department;
    private String province;
    private String district;
}
