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
@Table(name = "used_material")
public class UsedMaterial implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "count", nullable = false)
    private Integer count;

    @Column(name = "decommission")
    private Boolean decommission;

    @ToString.Exclude
    @ManyToOne
    private Order order;

    @ToString.Exclude
    @ManyToOne
    private Material material;

    @ToString.Exclude
    @ManyToOne
    private Employee employee;

}
