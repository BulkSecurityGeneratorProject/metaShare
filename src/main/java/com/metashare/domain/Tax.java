package com.metashare.domain;


import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Tax.
 */
@Entity
@Table(name = "tax")
public class Tax implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 居住地
     */
    @NotNull
    @Size(min = 0, max = 8)
    @ApiModelProperty(value = "居住地", required = true)
    @Column(name = "province_code", length = 8, nullable = false)
    private String provinceCode;

    @Column(name = "name")
    private Integer name;

    /**
     * 税率
     */
    @ApiModelProperty(value = "税率")
    @Column(name = "rate", precision = 21, scale = 2)
    private BigDecimal rate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public Tax provinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
        return this;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public Integer getName() {
        return name;
    }

    public Tax name(Integer name) {
        this.name = name;
        return this;
    }

    public void setName(Integer name) {
        this.name = name;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public Tax rate(BigDecimal rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tax)) {
            return false;
        }
        return id != null && id.equals(((Tax) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Tax{" +
            "id=" + getId() +
            ", provinceCode='" + getProvinceCode() + "'" +
            ", name=" + getName() +
            ", rate=" + getRate() +
            "}";
    }
}
