package com.sergey.zhuravlev.salon.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "authority")
public class Authority implements Serializable {

    @Id
    @Column(length = 50)
    private String name;

}
