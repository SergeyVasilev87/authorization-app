package com.github.SergeyVasilev87.first.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    @Enumerated(EnumType.STRING) //для возможности хранения enum в БД в виде String
    private Role role;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Transient
    private String passwordConfirm;
}
