package com.metashare.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Goods.
 */
@Entity
@Table(name = "goods")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商品名称ID
     */
    @NotNull
    @ApiModelProperty(value = "商品名称ID", required = true)
    @Column(name = "goods_id", nullable = false)
    private Long goodsId;

    /**
     * 商品名称
     */
    @Size(min = 0, max = 255)
    @ApiModelProperty(value = "商品名称")
    @Column(name = "name", length = 255)
    private String name;

    /**
     * 区域编码
     */
    @Size(min = 0, max = 6)
    @ApiModelProperty(value = "区域编码")
    @Column(name = "province_code", length = 6)
    private String provinceCode;

    /**
     * 税率
     */
    @ApiModelProperty(value = "税率")
    @Column(name = "rate", precision = 21, scale = 2)
    private BigDecimal rate;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @Column(name = "status")
    private Integer status;

    /**
     * 库存
     */
    @NotNull
    @ApiModelProperty(value = "库存", required = true)
    @Column(name = "stack", nullable = false)
    private Integer stack;

    /**
     * 单价
     */
    @NotNull
    @ApiModelProperty(value = "单价", required = true)
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JsonIgnoreProperties("goods")
    private Tax tax;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public Goods goodsId(Long goodsId) {
        this.goodsId = goodsId;
        return this;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getName() {
        return name;
    }

    public Goods name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public Goods provinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
        return this;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public Goods rate(BigDecimal rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Integer getStatus() {
        return status;
    }

    public Goods status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStack() {
        return stack;
    }

    public Goods stack(Integer stack) {
        this.stack = stack;
        return this;
    }

    public void setStack(Integer stack) {
        this.stack = stack;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Goods price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Tax getTax() {
        return tax;
    }

    public Goods tax(Tax tax) {
        this.tax = tax;
        return this;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Goods)) {
            return false;
        }
        return id != null && id.equals(((Goods) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Goods{" +
            "id=" + getId() +
            ", goodsId=" + getGoodsId() +
            ", name='" + getName() + "'" +
            ", provinceCode='" + getProvinceCode() + "'" +
            ", rate=" + getRate() +
            ", status=" + getStatus() +
            ", stack=" + getStack() +
            ", price=" + getPrice() +
            "}";
    }
}
