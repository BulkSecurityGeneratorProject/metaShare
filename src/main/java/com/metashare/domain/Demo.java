package com.metashare.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Demo.
 */
@Entity
@Table(name = "demo")
public class Demo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 0, max = 20)
    @Column(name = "name", length = 20, unique = true)
    private String name;

    @Column(name = "contant")
    private String contant;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Demo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContant() {
        return contant;
    }

    public Demo contant(String contant) {
        this.contant = contant;
        return this;
    }

    public void setContant(String contant) {
        this.contant = contant;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Demo)) {
            return false;
        }
        return id != null && id.equals(((Demo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Demo{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", contant='" + getContant() + "'" +
            "}";
    }
}
