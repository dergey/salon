package com.sergey.zhuravlev.salon.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "material_in_salon")
public class MaterialInSalon implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "count", nullable = false)
    private Integer count;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "salon_id")
    private Salon salon;

}
