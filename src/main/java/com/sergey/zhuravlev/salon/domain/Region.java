package com.sergey.zhuravlev.salon.domain;

import com.sergey.zhuravlev.salon.domain.enumeration.RegionStatus;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "region")
public class Region implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private RegionStatus status;

    @Column(name = "region_name", nullable = false)
    private String regionName;

}
