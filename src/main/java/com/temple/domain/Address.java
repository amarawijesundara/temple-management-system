package com.temple.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.temple.domain.enumeration.AddressType;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AddressType type;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "post_code")
    private String postCode;

    @ManyToOne
    @JsonIgnoreProperties(value = { "monkDetails", "addresses", "emergencyCDS", "guardianDS" }, allowSetters = true)
    private PersonalDetails personalDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address id(Long id) {
        this.id = id;
        return this;
    }

    public AddressType getType() {
        return this.type;
    }

    public Address type(AddressType type) {
        this.type = type;
        return this;
    }

    public void setType(AddressType type) {
        this.type = type;
    }

    public String getAddress1() {
        return this.address1;
    }

    public Address address1(String address1) {
        this.address1 = address1;
        return this;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return this.address2;
    }

    public Address address2(String address2) {
        this.address2 = address2;
        return this;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPostCode() {
        return this.postCode;
    }

    public Address postCode(String postCode) {
        this.postCode = postCode;
        return this;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public PersonalDetails getPersonalDetails() {
        return this.personalDetails;
    }

    public Address personalDetails(PersonalDetails personalDetails) {
        this.setPersonalDetails(personalDetails);
        return this;
    }

    public void setPersonalDetails(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return id != null && id.equals(((Address) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", address1='" + getAddress1() + "'" +
            ", address2='" + getAddress2() + "'" +
            ", postCode='" + getPostCode() + "'" +
            "}";
    }
}
