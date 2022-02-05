package com.sergey.zhuravlev.salon.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "salon")
public class Salon implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "location_id", unique = true)
    private Location location;

}
