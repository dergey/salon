package com.sergey.zhuravlev.salon.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "specialization")
public class Specialization implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "note")
    private String note;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

}
