package com.calapi.st.francis.assisi.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wedding_record")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeddingRecord {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "couple_uuid", nullable = false, unique = true, updatable = false)
    private String coupleUuid;
   
	private String groomFirstName;
	private String groomMiddleName;
	private String groomLastName;
	private LocalDate groomDateOfBirth;
	private String brideFirstName;
	private String brideMiddleName;
	private String brideLastName;
	private String brideDateOfBirth;
	private String witnesses;
	private LocalDate dateOfWedding;
	private String groomReligion;
	private String brideReligion;
	private String priestInCharge;
    private Integer bookNo;
    private Integer pageNo;
    private Integer lineNo;
    
    @PrePersist
    public void generateUuid() {
        if (coupleUuid == null) {
        	coupleUuid = UUID.randomUUID().toString();
        }
    }

	public synchronized Long getId() {
		return id;
	}
	public synchronized void setId(Long id) {
		this.id = id;
	}
	public synchronized String getCoupleUuid() {
		return coupleUuid;
	}
	public synchronized void setCoupleUuid(String coupleUuid) {
		this.coupleUuid = coupleUuid;
	}
	public synchronized String getGroomFirstName() {
		return groomFirstName;
	}
	public synchronized void setGroomFirstName(String groomFirstName) {
		this.groomFirstName = groomFirstName;
	}
	public synchronized String getGroomMiddleName() {
		return groomMiddleName;
	}
	public synchronized void setGroomMiddleName(String groomMiddleName) {
		this.groomMiddleName = groomMiddleName;
	}
	public synchronized String getGroomLastName() {
		return groomLastName;
	}
	public synchronized void setGroomLastName(String groomLastName) {
		this.groomLastName = groomLastName;
	}
	public synchronized LocalDate getGroomDateOfBirth() {
		return groomDateOfBirth;
	}
	public synchronized void setGroomDateOfBirth(LocalDate groomDateOfBirth) {
		this.groomDateOfBirth = groomDateOfBirth;
	}
	public synchronized String getBrideFirstName() {
		return brideFirstName;
	}
	public synchronized void setBrideFirstName(String brideFirstName) {
		this.brideFirstName = brideFirstName;
	}
	public synchronized String getBrideMiddleName() {
		return brideMiddleName;
	}
	public synchronized void setBrideMiddleName(String brideMiddleName) {
		this.brideMiddleName = brideMiddleName;
	}
	public synchronized String getBrideLastName() {
		return brideLastName;
	}
	public synchronized void setBrideLastName(String brideLastName) {
		this.brideLastName = brideLastName;
	}
	public synchronized String getBrideDateOfBirth() {
		return brideDateOfBirth;
	}
	public synchronized void setBrideDateOfBirth(String brideDateOfBirth) {
		this.brideDateOfBirth = brideDateOfBirth;
	}
	public synchronized String getWitnesses() {
		return witnesses;
	}
	public synchronized void setWitnesses(String witnesses) {
		this.witnesses = witnesses;
	}
	public synchronized LocalDate getDateOfWedding() {
		return dateOfWedding;
	}
	public synchronized void setDateOfWedding(LocalDate dateOfWedding) {
		this.dateOfWedding = dateOfWedding;
	}
	public synchronized String getGroomReligion() {
		return groomReligion;
	}
	public synchronized void setGroomReligion(String groomReligion) {
		this.groomReligion = groomReligion;
	}
	public synchronized String getBrideReligion() {
		return brideReligion;
	}
	public synchronized void setBrideReligion(String brideReligion) {
		this.brideReligion = brideReligion;
	}
	public synchronized String getPriestInCharge() {
		return priestInCharge;
	}
	public synchronized void setPriestInCharge(String priestInCharge) {
		this.priestInCharge = priestInCharge;
	}
	public synchronized Integer getBookNo() {
		return bookNo;
	}
	public synchronized void setBookNo(Integer bookNo) {
		this.bookNo = bookNo;
	}
	public synchronized Integer getPageNo() {
		return pageNo;
	}
	public synchronized void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public synchronized Integer getLineNo() {
		return lineNo;
	}
	public synchronized void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}
	@Override
	public String toString() {
		return "WeddingRecord [id=" + id + ", coupleUuid=" + coupleUuid + ", groomFirstName=" + groomFirstName
				+ ", groomMiddleName=" + groomMiddleName + ", groomLastName=" + groomLastName + ", groomDateOfBirth="
				+ groomDateOfBirth + ", brideFirstName=" + brideFirstName + ", brideMiddleName=" + brideMiddleName
				+ ", brideLastName=" + brideLastName + ", brideDateOfBirth=" + brideDateOfBirth + ", witnesses="
				+ witnesses + ", dateOfWedding=" + dateOfWedding + ", groomReligion=" + groomReligion
				+ ", brideReligion=" + brideReligion + ", priestInCharge=" + priestInCharge + ", bookNo=" + bookNo
				+ ", pageNo=" + pageNo + ", lineNo=" + lineNo + "]";
	}
	
	
}
