package com.sergey.zhuravlev.salon.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Specialization.
 */
@Entity
@Table(name = "specialization")
public class Specialization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JsonIgnoreProperties("specializations")
    private Employee employee;

    @ManyToOne
    @JsonIgnoreProperties("specializations")
    private Service service;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public Specialization note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Specialization employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Service getService() {
        return service;
    }

    public Specialization service(Service service) {
        this.service = service;
        return this;
    }

    public void setService(Service service) {
        this.service = service;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Specialization)) {
            return false;
        }
        return id != null && id.equals(((Specialization) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Specialization{" +
            "id=" + getId() +
            ", note='" + getNote() + "'" +
            "}";
    }
}
