package com.sergey.zhuravlev.salon.domain;

import com.sergey.zhuravlev.salon.domain.enumeration.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "service")
public class Service implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex", length = 20, nullable = false)
    private Sex sex;

    @Column(name = "price", precision = 11, scale = 2, nullable = false)
    private BigDecimal price;

}
