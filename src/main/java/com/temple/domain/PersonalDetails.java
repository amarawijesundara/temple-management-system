package com.temple.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.temple.domain.enumeration.Ethnicity;
import com.temple.domain.enumeration.Nationality;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The PersonalDetails entity.
 */
@Entity
@Table(name = "personal_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PersonalDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "nic")
    private String nic;

    @Enumerated(EnumType.STRING)
    @Column(name = "nationality")
    private Nationality nationality;

    @Enumerated(EnumType.STRING)
    @Column(name = "ethnicity")
    private Ethnicity ethnicity;

    @Column(name = "passport")
    private String passport;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "telephone")
    private Integer telephone;

    @Column(name = "mobile")
    private Integer mobile;

    @Column(name = "is_bikshu")
    private Boolean isBikshu;

    @Column(name = "is_anagarika")
    private Boolean isAnagarika;

    @Column(name = "is_upasaka")
    private Boolean isUpasaka;

    @Column(name = "notes")
    private String notes;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @OneToOne
    @JoinColumn(unique = true)
    private MonkDetails monkDetails;

    @OneToMany(mappedBy = "personalDetails")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "personalDetails" }, allowSetters = true)
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(mappedBy = "personalDetails")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "personalDetails" }, allowSetters = true)
    private Set<EmergencyContactDetails> emergencyCDS = new HashSet<>();

    @OneToMany(mappedBy = "personalDetails")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "personalDetails" }, allowSetters = true)
    private Set<GuardianDetails> guardianDS = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonalDetails id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public PersonalDetails name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return this.gender;
    }

    public PersonalDetails gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNic() {
        return this.nic;
    }

    public PersonalDetails nic(String nic) {
        this.nic = nic;
        return this;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public Nationality getNationality() {
        return this.nationality;
    }

    public PersonalDetails nationality(Nationality nationality) {
        this.nationality = nationality;
        return this;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    public Ethnicity getEthnicity() {
        return this.ethnicity;
    }

    public PersonalDetails ethnicity(Ethnicity ethnicity) {
        this.ethnicity = ethnicity;
        return this;
    }

    public void setEthnicity(Ethnicity ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getPassport() {
        return this.passport;
    }

    public PersonalDetails passport(String passport) {
        this.passport = passport;
        return this;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public PersonalDetails dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getTelephone() {
        return this.telephone;
    }

    public PersonalDetails telephone(Integer telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public Integer getMobile() {
        return this.mobile;
    }

    public PersonalDetails mobile(Integer mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    public Boolean getIsBikshu() {
        return this.isBikshu;
    }

    public PersonalDetails isBikshu(Boolean isBikshu) {
        this.isBikshu = isBikshu;
        return this;
    }

    public void setIsBikshu(Boolean isBikshu) {
        this.isBikshu = isBikshu;
    }

    public Boolean getIsAnagarika() {
        return this.isAnagarika;
    }

    public PersonalDetails isAnagarika(Boolean isAnagarika) {
        this.isAnagarika = isAnagarika;
        return this;
    }

    public void setIsAnagarika(Boolean isAnagarika) {
        this.isAnagarika = isAnagarika;
    }

    public Boolean getIsUpasaka() {
        return this.isUpasaka;
    }

    public PersonalDetails isUpasaka(Boolean isUpasaka) {
        this.isUpasaka = isUpasaka;
        return this;
    }

    public void setIsUpasaka(Boolean isUpasaka) {
        this.isUpasaka = isUpasaka;
    }

    public String getNotes() {
        return this.notes;
    }

    public PersonalDetails notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public PersonalDetails createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getUpdatedDate() {
        return this.updatedDate;
    }

    public PersonalDetails updatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public MonkDetails getMonkDetails() {
        return this.monkDetails;
    }

    public PersonalDetails monkDetails(MonkDetails monkDetails) {
        this.setMonkDetails(monkDetails);
        return this;
    }

    public void setMonkDetails(MonkDetails monkDetails) {
        this.monkDetails = monkDetails;
    }

    public Set<Address> getAddresses() {
        return this.addresses;
    }

    public PersonalDetails addresses(Set<Address> addresses) {
        this.setAddresses(addresses);
        return this;
    }

    public PersonalDetails addAddress(Address address) {
        this.addresses.add(address);
        address.setPersonalDetails(this);
        return this;
    }

    public PersonalDetails removeAddress(Address address) {
        this.addresses.remove(address);
        address.setPersonalDetails(null);
        return this;
    }

    public void setAddresses(Set<Address> addresses) {
        if (this.addresses != null) {
            this.addresses.forEach(i -> i.setPersonalDetails(null));
        }
        if (addresses != null) {
            addresses.forEach(i -> i.setPersonalDetails(this));
        }
        this.addresses = addresses;
    }

    public Set<EmergencyContactDetails> getEmergencyCDS() {
        return this.emergencyCDS;
    }

    public PersonalDetails emergencyCDS(Set<EmergencyContactDetails> emergencyContactDetails) {
        this.setEmergencyCDS(emergencyContactDetails);
        return this;
    }

    public PersonalDetails addEmergencyCD(EmergencyContactDetails emergencyContactDetails) {
        this.emergencyCDS.add(emergencyContactDetails);
        emergencyContactDetails.setPersonalDetails(this);
        return this;
    }

    public PersonalDetails removeEmergencyCD(EmergencyContactDetails emergencyContactDetails) {
        this.emergencyCDS.remove(emergencyContactDetails);
        emergencyContactDetails.setPersonalDetails(null);
        return this;
    }

    public void setEmergencyCDS(Set<EmergencyContactDetails> emergencyContactDetails) {
        if (this.emergencyCDS != null) {
            this.emergencyCDS.forEach(i -> i.setPersonalDetails(null));
        }
        if (emergencyContactDetails != null) {
            emergencyContactDetails.forEach(i -> i.setPersonalDetails(this));
        }
        this.emergencyCDS = emergencyContactDetails;
    }

    public Set<GuardianDetails> getGuardianDS() {
        return this.guardianDS;
    }

    public PersonalDetails guardianDS(Set<GuardianDetails> guardianDetails) {
        this.setGuardianDS(guardianDetails);
        return this;
    }

    public PersonalDetails addGuardianD(GuardianDetails guardianDetails) {
        this.guardianDS.add(guardianDetails);
        guardianDetails.setPersonalDetails(this);
        return this;
    }

    public PersonalDetails removeGuardianD(GuardianDetails guardianDetails) {
        this.guardianDS.remove(guardianDetails);
        guardianDetails.setPersonalDetails(null);
        return this;
    }

    public void setGuardianDS(Set<GuardianDetails> guardianDetails) {
        if (this.guardianDS != null) {
            this.guardianDS.forEach(i -> i.setPersonalDetails(null));
        }
        if (guardianDetails != null) {
            guardianDetails.forEach(i -> i.setPersonalDetails(this));
        }
        this.guardianDS = guardianDetails;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonalDetails)) {
            return false;
        }
        return id != null && id.equals(((PersonalDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonalDetails{" +
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
            "}";
    }
}
