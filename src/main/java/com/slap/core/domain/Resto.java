package com.slap.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Resto.
 */
@Entity
@Table(name = "resto")
public class Resto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "resto_name")
    private String restoName;

    @Column(name = "resto_address")
    private String restoAddress;

    @Column(name = "resto_phone_number")
    private String restoPhoneNumber;

    @OneToMany(mappedBy = "resto")
    @JsonIgnore
    private Set<Menu> restomenus = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRestoName() {
        return restoName;
    }

    public Resto restoName(String restoName) {
        this.restoName = restoName;
        return this;
    }

    public void setRestoName(String restoName) {
        this.restoName = restoName;
    }

    public String getRestoAddress() {
        return restoAddress;
    }

    public Resto restoAddress(String restoAddress) {
        this.restoAddress = restoAddress;
        return this;
    }

    public void setRestoAddress(String restoAddress) {
        this.restoAddress = restoAddress;
    }

    public String getRestoPhoneNumber() {
        return restoPhoneNumber;
    }

    public Resto restoPhoneNumber(String restoPhoneNumber) {
        this.restoPhoneNumber = restoPhoneNumber;
        return this;
    }

    public void setRestoPhoneNumber(String restoPhoneNumber) {
        this.restoPhoneNumber = restoPhoneNumber;
    }

    public Set<Menu> getRestomenus() {
        return restomenus;
    }

    public Resto restomenus(Set<Menu> menus) {
        this.restomenus = menus;
        return this;
    }

    public Resto addRestomenus(Menu menu) {
        this.restomenus.add(menu);
        menu.setResto(this);
        return this;
    }

    public Resto removeRestomenus(Menu menu) {
        this.restomenus.remove(menu);
        menu.setResto(null);
        return this;
    }

    public void setRestomenus(Set<Menu> menus) {
        this.restomenus = menus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Resto resto = (Resto) o;
        if (resto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Resto{" +
            "id=" + getId() +
            ", restoName='" + getRestoName() + "'" +
            ", restoAddress='" + getRestoAddress() + "'" +
            ", restoPhoneNumber='" + getRestoPhoneNumber() + "'" +
            "}";
    }
}
