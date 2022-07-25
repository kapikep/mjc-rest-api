package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "first_name", nullable = false, length = 25)
    private String firstName;

    @Column(name = "second_name", nullable = false, length = 25)
    private String secondName;

    @Column(name = "login", nullable = false, length = 25)
    private String login;

    @Column(name = "password", nullable = false, length = 25)
    private String password;

    @Column(name = "phone_number", nullable = false, length = 17)
    private String phoneNumber;

    @OneToMany(mappedBy = "user")
    private List<OrderForGiftCertificateEntity> orders = new ArrayList<>();
}
