package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = "orders")
@Entity
@Table(name = "users")
public class UserEntity extends AuditingEntity implements Serializable {
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

    public UserEntity(long id, String firstName, String secondName,
                      String login, String password, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.login = login;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
