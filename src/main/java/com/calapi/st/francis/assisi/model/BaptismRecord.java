package com.calapi.st.francis.assisi.model;

import jakarta.persistence.Entity;
import lombok.Data;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "baptism_records")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaptismRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "child_uuid", nullable = false, unique = true, updatable = false)
	private String childUuid;

    // Personal Information
    private String firstName;
    private String middleName;
    private String lastName;

    private LocalDate dateOfBirth;
    private String placeOfBirth;
    private LocalDate dateOfBaptism;
    private String placeOfBaptism;

    // Father's Name
    private String fatherFirstName;
    private String fatherMiddleName;
    private String fatherLastName;

    // Mother's Maiden Name
    private String motherFirstName;
    private String motherMiddleName;
    private String motherLastName;

    private String parentsAddress;
    
	private String ageCategory;

    // Sponsors & Minister
    @Column(length = 1000)
    private String sponsors;

    private String minister;
    private String priestInCharge;
    
    private Integer bookNo;
    private Integer pageNo;
    private Integer lineNo;
    
    @PrePersist
    public void generateUuid() {
        if (childUuid == null) {
            childUuid = UUID.randomUUID().toString();
        }
    }

    
	public synchronized String getPriestInCharge() {
		return priestInCharge;
	}


	public synchronized void setPriestInCharge(String priestInCharge) {
		this.priestInCharge = priestInCharge;
	}


	public synchronized String getChildUuid() {
		return childUuid;
	}
	public synchronized void setChildUuid(String childUuid) {
		this.childUuid = childUuid;
	}
	public Integer getBookNo() {
		return bookNo;
	}

	public void setBookNo(Integer bookNo) {
		this.bookNo = bookNo;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getLineNo() {
		return lineNo;
	}

	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}

	public String getAgeCategory() {
		return ageCategory;
	}

	public void setAgeCategory(String ageCategory) {
		this.ageCategory = ageCategory;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public LocalDate getDateOfBaptism() {
		return dateOfBaptism;
	}

	public void setDateOfBaptism(LocalDate dateOfBaptism) {
		this.dateOfBaptism = dateOfBaptism;
	}

	public String getPlaceOfBaptism() {
		return placeOfBaptism;
	}

	public void setPlaceOfBaptism(String placeOfBaptism) {
		this.placeOfBaptism = placeOfBaptism;
	}

	public String getFatherFirstName() {
		return fatherFirstName;
	}

	public void setFatherFirstName(String fatherFirstName) {
		this.fatherFirstName = fatherFirstName;
	}

	public String getFatherMiddleName() {
		return fatherMiddleName;
	}

	public void setFatherMiddleName(String fatherMiddleName) {
		this.fatherMiddleName = fatherMiddleName;
	}

	public String getFatherLastName() {
		return fatherLastName;
	}

	public void setFatherLastName(String fatherLastName) {
		this.fatherLastName = fatherLastName;
	}

	public String getMotherFirstName() {
		return motherFirstName;
	}

	public void setMotherFirstName(String motherFirstName) {
		this.motherFirstName = motherFirstName;
	}

	public String getMotherMiddleName() {
		return motherMiddleName;
	}

	public void setMotherMiddleName(String motherMiddleName) {
		this.motherMiddleName = motherMiddleName;
	}

	public String getMotherLastName() {
		return motherLastName;
	}

	public void setMotherLastName(String motherLastName) {
		this.motherLastName = motherLastName;
	}

	public String getSponsors() {
		return sponsors;
	}

	public void setSponsors(String sponsors) {
		this.sponsors = sponsors;
	}

	public String getMinister() {
		return minister;
	}

	public void setMinister(String minister) {
		this.minister = minister;
	}

	public synchronized String getParentsAddress() {
		return parentsAddress;
	}

	public synchronized void setParentsAddress(String parentsAddress) {
		this.parentsAddress = parentsAddress;
	}


	@Override
	public String toString() {
		return "BaptismRecord [id=" + id + ", childUuid=" + childUuid + ", firstName=" + firstName + ", middleName="
				+ middleName + ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth + ", placeOfBirth="
				+ placeOfBirth + ", dateOfBaptism=" + dateOfBaptism + ", placeOfBaptism=" + placeOfBaptism
				+ ", fatherFirstName=" + fatherFirstName + ", fatherMiddleName=" + fatherMiddleName
				+ ", fatherLastName=" + fatherLastName + ", motherFirstName=" + motherFirstName + ", motherMiddleName="
				+ motherMiddleName + ", motherLastName=" + motherLastName + ", parentsAddress=" + parentsAddress
				+ ", ageCategory=" + ageCategory + ", sponsors=" + sponsors + ", minister=" + minister
				+ ", priestInCharge=" + priestInCharge + ", bookNo=" + bookNo + ", pageNo=" + pageNo + ", lineNo="
				+ lineNo + "]";
	}
}
