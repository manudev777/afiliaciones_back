package com.rtecnico.afiliaciones.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    private String username;
    private String password;

    @OneToOne
    private Agent agent;
}