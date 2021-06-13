package com.temple.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.temple.domain.MonkDetails} entity.
 */
public class MonkDetailsDTO implements Serializable {

    private Long id;

    private String nameEnglish;

    private String nameSinhala;

    private String pawidiNo;

    private String samaneraNo;

    private String upasampadaNo;

    private LocalDate pawidiDate;

    private LocalDate upasampadaDate;

    private LocalDate createdDate;

    private LocalDate updatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public String getNameSinhala() {
        return nameSinhala;
    }

    public void setNameSinhala(String nameSinhala) {
        this.nameSinhala = nameSinhala;
    }

    public String getPawidiNo() {
        return pawidiNo;
    }

    public void setPawidiNo(String pawidiNo) {
        this.pawidiNo = pawidiNo;
    }

    public String getSamaneraNo() {
        return samaneraNo;
    }

    public void setSamaneraNo(String samaneraNo) {
        this.samaneraNo = samaneraNo;
    }

    public String getUpasampadaNo() {
        return upasampadaNo;
    }

    public void setUpasampadaNo(String upasampadaNo) {
        this.upasampadaNo = upasampadaNo;
    }

    public LocalDate getPawidiDate() {
        return pawidiDate;
    }

    public void setPawidiDate(LocalDate pawidiDate) {
        this.pawidiDate = pawidiDate;
    }

    public LocalDate getUpasampadaDate() {
        return upasampadaDate;
    }

    public void setUpasampadaDate(LocalDate upasampadaDate) {
        this.upasampadaDate = upasampadaDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MonkDetailsDTO)) {
            return false;
        }

        MonkDetailsDTO monkDetailsDTO = (MonkDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, monkDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MonkDetailsDTO{" +
            "id=" + getId() +
            ", nameEnglish='" + getNameEnglish() + "'" +
            ", nameSinhala='" + getNameSinhala() + "'" +
            ", pawidiNo='" + getPawidiNo() + "'" +
            ", samaneraNo='" + getSamaneraNo() + "'" +
            ", upasampadaNo='" + getUpasampadaNo() + "'" +
            ", pawidiDate='" + getPawidiDate() + "'" +
            ", upasampadaDate='" + getUpasampadaDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
