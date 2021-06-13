package com.temple.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MonkDetails.
 */
@Entity
@Table(name = "monk_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MonkDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_english")
    private String nameEnglish;

    @Column(name = "name_sinhala")
    private String nameSinhala;

    @Column(name = "pawidi_no")
    private String pawidiNo;

    @Column(name = "samanera_no")
    private String samaneraNo;

    @Column(name = "upasampada_no")
    private String upasampadaNo;

    @Column(name = "pawidi_date")
    private LocalDate pawidiDate;

    @Column(name = "upasampada_date")
    private LocalDate upasampadaDate;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MonkDetails id(Long id) {
        this.id = id;
        return this;
    }

    public String getNameEnglish() {
        return this.nameEnglish;
    }

    public MonkDetails nameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
        return this;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public String getNameSinhala() {
        return this.nameSinhala;
    }

    public MonkDetails nameSinhala(String nameSinhala) {
        this.nameSinhala = nameSinhala;
        return this;
    }

    public void setNameSinhala(String nameSinhala) {
        this.nameSinhala = nameSinhala;
    }

    public String getPawidiNo() {
        return this.pawidiNo;
    }

    public MonkDetails pawidiNo(String pawidiNo) {
        this.pawidiNo = pawidiNo;
        return this;
    }

    public void setPawidiNo(String pawidiNo) {
        this.pawidiNo = pawidiNo;
    }

    public String getSamaneraNo() {
        return this.samaneraNo;
    }

    public MonkDetails samaneraNo(String samaneraNo) {
        this.samaneraNo = samaneraNo;
        return this;
    }

    public void setSamaneraNo(String samaneraNo) {
        this.samaneraNo = samaneraNo;
    }

    public String getUpasampadaNo() {
        return this.upasampadaNo;
    }

    public MonkDetails upasampadaNo(String upasampadaNo) {
        this.upasampadaNo = upasampadaNo;
        return this;
    }

    public void setUpasampadaNo(String upasampadaNo) {
        this.upasampadaNo = upasampadaNo;
    }

    public LocalDate getPawidiDate() {
        return this.pawidiDate;
    }

    public MonkDetails pawidiDate(LocalDate pawidiDate) {
        this.pawidiDate = pawidiDate;
        return this;
    }

    public void setPawidiDate(LocalDate pawidiDate) {
        this.pawidiDate = pawidiDate;
    }

    public LocalDate getUpasampadaDate() {
        return this.upasampadaDate;
    }

    public MonkDetails upasampadaDate(LocalDate upasampadaDate) {
        this.upasampadaDate = upasampadaDate;
        return this;
    }

    public void setUpasampadaDate(LocalDate upasampadaDate) {
        this.upasampadaDate = upasampadaDate;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public MonkDetails createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getUpdatedDate() {
        return this.updatedDate;
    }

    public MonkDetails updatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MonkDetails)) {
            return false;
        }
        return id != null && id.equals(((MonkDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MonkDetails{" +
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
