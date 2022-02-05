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
@Table(name = "country")
public class Country implements Serializable {

    @Id
    @Column(name = "code", length = 2, nullable = false)
    private String code;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

}
