package com.slap.core.domain;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "starter")
    private String starter;

    @Column(name = "main_course")
    private String mainCourse;

    @Column(name = "sweeties")
    private String sweeties;

    @Column(name = "beveries")
    private String beveries;

    @ManyToOne
    private Resto resto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStarter() {
        return starter;
    }

    public Menu starter(String starter) {
        this.starter = starter;
        return this;
    }

    public void setStarter(String starter) {
        this.starter = starter;
    }

    public String getMainCourse() {
        return mainCourse;
    }

    public Menu mainCourse(String mainCourse) {
        this.mainCourse = mainCourse;
        return this;
    }

    public void setMainCourse(String mainCourse) {
        this.mainCourse = mainCourse;
    }

    public String getSweeties() {
        return sweeties;
    }

    public Menu sweeties(String sweeties) {
        this.sweeties = sweeties;
        return this;
    }

    public void setSweeties(String sweeties) {
        this.sweeties = sweeties;
    }

    public String getBeveries() {
        return beveries;
    }

    public Menu beveries(String beveries) {
        this.beveries = beveries;
        return this;
    }

    public void setBeveries(String beveries) {
        this.beveries = beveries;
    }

    public Resto getResto() {
        return resto;
    }

    public Menu resto(Resto resto) {
        this.resto = resto;
        return this;
    }

    public void setResto(Resto resto) {
        this.resto = resto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Menu menu = (Menu) o;
        if (menu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), menu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Menu{" +
            "id=" + getId() +
            ", starter='" + getStarter() + "'" +
            ", mainCourse='" + getMainCourse() + "'" +
            ", sweeties='" + getSweeties() + "'" +
            ", beveries='" + getBeveries() + "'" +
            "}";
    }
}
