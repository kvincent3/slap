package com.slap.core.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "client_firt_name")
    private String clientFirtName;

    @Column(name = "client_sur_name")
    private String clientSurName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientFirtName() {
        return clientFirtName;
    }

    public Client clientFirtName(String clientFirtName) {
        this.clientFirtName = clientFirtName;
        return this;
    }

    public void setClientFirtName(String clientFirtName) {
        this.clientFirtName = clientFirtName;
    }

    public String getClientSurName() {
        return clientSurName;
    }

    public Client clientSurName(String clientSurName) {
        this.clientSurName = clientSurName;
        return this;
    }

    public void setClientSurName(String clientSurName) {
        this.clientSurName = clientSurName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client client = (Client) o;
        if (client.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), client.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", clientFirtName='" + getClientFirtName() + "'" +
            ", clientSurName='" + getClientSurName() + "'" +
            "}";
    }
}
