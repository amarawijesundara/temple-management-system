package com.temple.service.dto;

import com.temple.domain.enumeration.AddressType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.temple.domain.Address} entity.
 */
public class AddressDTO implements Serializable {

    private Long id;

    private AddressType type;

    private String address1;

    private String address2;

    private String postCode;

    private PersonalDetailsDTO personalDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddressType getType() {
        return type;
    }

    public void setType(AddressType type) {
        this.type = type;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
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
        if (!(o instanceof AddressDTO)) {
            return false;
        }

        AddressDTO addressDTO = (AddressDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, addressDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", address1='" + getAddress1() + "'" +
            ", address2='" + getAddress2() + "'" +
            ", postCode='" + getPostCode() + "'" +
            ", personalDetails=" + getPersonalDetails() +
            "}";
    }
}
