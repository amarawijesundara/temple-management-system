package com.temple.service.dto;

import com.temple.domain.enumeration.Ethnicity;
import com.temple.domain.enumeration.Nationality;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.temple.domain.PersonalDetails} entity.
 */
@ApiModel(description = "The PersonalDetails entity.")
public class PersonalDetailsDTO implements Serializable {

    private Long id;

    private String name;

    private String gender;

    private String nic;

    private Nationality nationality;

    private Ethnicity ethnicity;

    private String passport;

    private LocalDate dateOfBirth;

    private Integer telephone;

    private Integer mobile;

    private Boolean isBikshu;

    private Boolean isAnagarika;

    private Boolean isUpasaka;

    private String notes;

    private LocalDate createdDate;

    private LocalDate updatedDate;

    private MonkDetailsDTO monkDetails;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public Nationality getNationality() {
        return nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    public Ethnicity getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(Ethnicity ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public Integer getMobile() {
        return mobile;
    }

    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    public Boolean getIsBikshu() {
        return isBikshu;
    }

    public void setIsBikshu(Boolean isBikshu) {
        this.isBikshu = isBikshu;
    }

    public Boolean getIsAnagarika() {
        return isAnagarika;
    }

    public void setIsAnagarika(Boolean isAnagarika) {
        this.isAnagarika = isAnagarika;
    }

    public Boolean getIsUpasaka() {
        return isUpasaka;
    }

    public void setIsUpasaka(Boolean isUpasaka) {
        this.isUpasaka = isUpasaka;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public MonkDetailsDTO getMonkDetails() {
        return monkDetails;
    }

    public void setMonkDetails(MonkDetailsDTO monkDetails) {
        this.monkDetails = monkDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonalDetailsDTO)) {
            return false;
        }

        PersonalDetailsDTO personalDetailsDTO = (PersonalDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, personalDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonalDetailsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", gender='" + getGender() + "'" +
            ", nic='" + getNic() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", ethnicity='" + getEthnicity() + "'" +
            ", passport='" + getPassport() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", telephone=" + getTelephone() +
            ", mobile=" + getMobile() +
            ", isBikshu='" + getIsBikshu() + "'" +
            ", isAnagarika='" + getIsAnagarika() + "'" +
            ", isUpasaka='" + getIsUpasaka() + "'" +
            ", notes='" + getNotes() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", monkDetails=" + getMonkDetails() +
            "}";
    }
}
