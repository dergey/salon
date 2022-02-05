package com.sergey.zhuravlev.salon.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "hire_date", nullable = false)
    private Instant hireDate;

    @Column(name = "salary", precision = 11, scale = 2, nullable = false)
    private BigDecimal salary;

    @Column(name = "commission_pct", precision = 11, scale = 2, nullable = false)
    private BigDecimal commissionPct;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "salon_id")
    private Salon salon;

}
