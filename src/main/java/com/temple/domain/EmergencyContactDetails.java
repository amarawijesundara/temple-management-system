package com.temple.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.temple.domain.enumeration.ContactType;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EmergencyContactDetails.
 */
@Entity
@Table(name = "emergency_contact_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmergencyContactDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "telephone")
    private Integer telephone;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "contact_type")
    private ContactType contactType;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

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

    public EmergencyContactDetails id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public EmergencyContactDetails name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public EmergencyContactDetails address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getTelephone() {
        return this.telephone;
    }

    public EmergencyContactDetails telephone(Integer telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return this.email;
    }

    public EmergencyContactDetails email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ContactType getContactType() {
        return this.contactType;
    }

    public EmergencyContactDetails contactType(ContactType contactType) {
        this.contactType = contactType;
        return this;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public EmergencyContactDetails createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getUpdatedDate() {
        return this.updatedDate;
    }

    public EmergencyContactDetails updatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public PersonalDetails getPersonalDetails() {
        return this.personalDetails;
    }

    public EmergencyContactDetails personalDetails(PersonalDetails personalDetails) {
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
        if (!(o instanceof EmergencyContactDetails)) {
            return false;
        }
        return id != null && id.equals(((EmergencyContactDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmergencyContactDetails{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", telephone=" + getTelephone() +
            ", email='" + getEmail() + "'" +
            ", contactType='" + getContactType() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
