package com.metashare.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 商品名称
     */
    @NotNull
    @ApiModelProperty(value = "商品名称", required = true)
    @Column(name = "goods_name", nullable = false)
    private String goodsName;

    /**
     * 数量ID
     */
    @NotNull
    @ApiModelProperty(value = "数量ID", required = true)
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    /**
     * 小计
     */
    @NotNull
    @ApiModelProperty(value = "小计", required = true)
    @Column(name = "subtotal_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal subtotalAmount;

    /**
     * 单价
     */
    @NotNull
    @ApiModelProperty(value = "单价", required = true)
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;

    /**
     * 税款小计
     */
    @NotNull
    @ApiModelProperty(value = "税款小计", required = true)
    @Column(name = "tax_total_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal taxTotalAmount;

    /**
     * 税率
     */
    @NotNull
    @ApiModelProperty(value = "税率", required = true)
    @Column(name = "tax_tate", precision = 21, scale = 2, nullable = false)
    private BigDecimal taxTate;

    /**
     * 总计
     */
    @NotNull
    @ApiModelProperty(value = "总计", required = true)
    @Column(name = "sum_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal sumAmount;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "create_time")
    private Instant createTime;

    @Column(name = "updateby")
    private Long updateby;

    @Column(name = "update_time")
    private Instant updateTime;

    @Column(name = "version")
    private Integer version;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @Column(name = "status")
    private Integer status;

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private Goods goods;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public Order orderCode(String orderCode) {
        this.orderCode = orderCode;
        return this;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public Order goodsName(String goodsName) {
        this.goodsName = goodsName;
        return this;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Order quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotalAmount() {
        return subtotalAmount;
    }

    public Order subtotalAmount(BigDecimal subtotalAmount) {
        this.subtotalAmount = subtotalAmount;
        return this;
    }

    public void setSubtotalAmount(BigDecimal subtotalAmount) {
        this.subtotalAmount = subtotalAmount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Order price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTaxTotalAmount() {
        return taxTotalAmount;
    }

    public Order taxTotalAmount(BigDecimal taxTotalAmount) {
        this.taxTotalAmount = taxTotalAmount;
        return this;
    }

    public void setTaxTotalAmount(BigDecimal taxTotalAmount) {
        this.taxTotalAmount = taxTotalAmount;
    }

    public BigDecimal getTaxTate() {
        return taxTate;
    }

    public Order taxTate(BigDecimal taxTate) {
        this.taxTate = taxTate;
        return this;
    }

    public void setTaxTate(BigDecimal taxTate) {
        this.taxTate = taxTate;
    }

    public BigDecimal getSumAmount() {
        return sumAmount;
    }

    public Order sumAmount(BigDecimal sumAmount) {
        this.sumAmount = sumAmount;
        return this;
    }

    public void setSumAmount(BigDecimal sumAmount) {
        this.sumAmount = sumAmount;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public Order createBy(Long createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public Order createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateby() {
        return updateby;
    }

    public Order updateby(Long updateby) {
        this.updateby = updateby;
        return this;
    }

    public void setUpdateby(Long updateby) {
        this.updateby = updateby;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public Order updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public Order version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getStatus() {
        return status;
    }

    public Order status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Goods getGoods() {
        return goods;
    }

    public Order goods(Goods goods) {
        this.goods = goods;
        return this;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", orderCode='" + getOrderCode() + "'" +
            ", goodsName='" + getGoodsName() + "'" +
            ", quantity=" + getQuantity() +
            ", subtotalAmount=" + getSubtotalAmount() +
            ", price=" + getPrice() +
            ", taxTotalAmount=" + getTaxTotalAmount() +
            ", taxTate=" + getTaxTate() +
            ", sumAmount=" + getSumAmount() +
            ", createBy=" + getCreateBy() +
            ", createTime='" + getCreateTime() + "'" +
            ", updateby=" + getUpdateby() +
            ", updateTime='" + getUpdateTime() + "'" +
            ", version=" + getVersion() +
            ", status=" + getStatus() +
            "}";
    }
}
