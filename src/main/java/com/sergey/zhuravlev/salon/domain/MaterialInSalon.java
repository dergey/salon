package com.sergey.zhuravlev.salon.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A MaterialInSalon.
 */
@Entity
@Table(name = "material_in_salon")
public class MaterialInSalon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "count", nullable = false)
    private Integer count;

    @ManyToOne
    @JsonIgnoreProperties("materialInSalons")
    private Material material;

    @ManyToOne
    @JsonIgnoreProperties("materialInSalons")
    private Salon salon;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public MaterialInSalon count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Material getMaterial() {
        return material;
    }

    public MaterialInSalon material(Material material) {
        this.material = material;
        return this;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Salon getSalon() {
        return salon;
    }

    public MaterialInSalon salon(Salon salon) {
        this.salon = salon;
        return this;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MaterialInSalon)) {
            return false;
        }
        return id != null && id.equals(((MaterialInSalon) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MaterialInSalon{" +
            "id=" + getId() +
            ", count=" + getCount() +
            "}";
    }
}
