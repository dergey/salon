package com.sergey.zhuravlev.salon.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.sergey.zhuravlev.salon.domain.enumeration.Sex;
import lombok.*;

/**
 * A Client.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sex", length = 20, nullable = false)
    private Sex sex;

}

