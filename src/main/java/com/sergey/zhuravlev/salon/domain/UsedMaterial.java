package com.sergey.zhuravlev.salon.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A UsedMaterial.
 */
@Entity
@Table(name = "used_material")
public class UsedMaterial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "count", nullable = false)
    private Integer count;

    @Column(name = "decommission")
    private Boolean decommission;

    @ManyToOne
    @JsonIgnoreProperties("usedMaterials")
    private Order order;

    @ManyToOne
    @JsonIgnoreProperties("usedMaterials")
    private Material material;

    @ManyToOne
    @JsonIgnoreProperties("usedMaterials")
    private Employee employee;

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

    public UsedMaterial count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Boolean isDecommission() {
        return decommission;
    }

    public UsedMaterial decommission(Boolean decommission) {
        this.decommission = decommission;
        return this;
    }

    public void setDecommission(Boolean decommission) {
        this.decommission = decommission;
    }

    public Order getOrder() {
        return order;
    }

    public UsedMaterial order(Order order) {
        this.order = order;
        return this;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Material getMaterial() {
        return material;
    }

    public UsedMaterial material(Material material) {
        this.material = material;
        return this;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Employee getEmployee() {
        return employee;
    }

    public UsedMaterial employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsedMaterial)) {
            return false;
        }
        return id != null && id.equals(((UsedMaterial) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UsedMaterial{" +
            "id=" + getId() +
            ", count=" + getCount() +
            ", decommission='" + isDecommission() + "'" +
            "}";
    }
}
