package com.rtecnico.afiliaciones.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "agent")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;
    private String lastnameSecond;
    private String email;
    private String phone;
    private String ruc;
    private String department;
    private String province;
    private String district;

    @JsonIgnore
    @OneToOne(mappedBy = "agent")
    private Users user;
}

