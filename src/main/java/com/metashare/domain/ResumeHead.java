package com.metashare.domain;


import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ResumeHead.
 */
@Entity
@Table(name = "resume_head")
public class ResumeHead implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商品名称ID
     */
    @NotNull
    @Size(min = 0, max = 32)
    @ApiModelProperty(value = "商品名称ID", required = true)
    @Column(name = "name", length = 32, nullable = false)
    private String name;

    /**
     * 商品名称ID
     */
    @NotNull
    @Size(min = 0, max = 16)
    @ApiModelProperty(value = "商品名称ID", required = true)
    @Column(name = "mobile", length = 16, nullable = false)
    private String mobile;

    /**
     * 电子邮件D
     */
    @NotNull
    @Size(min = 0, max = 32)
    @ApiModelProperty(value = "电子邮件D", required = true)
    @Column(name = "email", length = 32, nullable = false)
    private String email;

    /**
     * 年龄
     */
    @NotNull
    @ApiModelProperty(value = "年龄", required = true)
    @Column(name = "age", nullable = false)
    private Integer age;

    /**
     * 相片
     */
    @ApiModelProperty(value = "相片")
    @Column(name = "photo")
    private String photo;

    /**
     * 居住地
     */
    @NotNull
    @Size(min = 0, max = 256)
    @ApiModelProperty(value = "居住地", required = true)
    @Column(name = "residence", length = 256, nullable = false)
    private String residence;

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

    public ResumeHead name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public ResumeHead mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public ResumeHead email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public ResumeHead age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhoto() {
        return photo;
    }

    public ResumeHead photo(String photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getResidence() {
        return residence;
    }

    public ResumeHead residence(String residence) {
        this.residence = residence;
        return this;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResumeHead)) {
            return false;
        }
        return id != null && id.equals(((ResumeHead) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ResumeHead{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", email='" + getEmail() + "'" +
            ", age=" + getAge() +
            ", photo='" + getPhoto() + "'" +
            ", residence='" + getResidence() + "'" +
            "}";
    }
}
