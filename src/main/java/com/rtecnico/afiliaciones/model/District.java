package com.rtecnico.afiliaciones.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "district")
@Entity
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;

}
