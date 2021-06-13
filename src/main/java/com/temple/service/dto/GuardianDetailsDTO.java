package com.temple.service.dto;

import com.temple.domain.enumeration.ContactType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.temple.domain.GuardianDetails} entity.
 */
public class GuardianDetailsDTO implements Serializable {

    private Long id;

    private String name;

    private String address;

    private ContactType contactType;

    private LocalDate createdDate;

    private LocalDate updatedDate;

    private PersonalDetailsDTO personalDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public PersonalDetailsDTO getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(PersonalDetailsDTO personalDetails) {
        this.personalDetails = personalDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GuardianDetailsDTO)) {
            return false;
        }

        GuardianDetailsDTO guardianDetailsDTO = (GuardianDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, guardianDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GuardianDetailsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", contactType='" + getContactType() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", personalDetails=" + getPersonalDetails() +
            "}";
    }
}
