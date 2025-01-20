package com.rtecnico.afiliaciones.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDTO {
    @NotBlank(message = "Name is required")
    @Size(min = 2, message = "Name must be at least 2 characters")
    private String name;

    @NotBlank(message = "Last name is required")
    private String lastname;

    @NotBlank(message = "Second last name is required")
    private String lastnameSecond;

    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{9}$", message = "Phone number must be 9 digits")
    private String phone;

    @NotBlank(message = "RUC is required")
    private String ruc;

    @NotBlank(message = "Department is required")
    private String department;

    @NotBlank(message = "Province is required")
    private String province;

    @NotBlank(message = "District is required")
    private String district;
}
