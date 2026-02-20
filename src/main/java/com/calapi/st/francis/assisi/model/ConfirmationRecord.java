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
@Table(name = "confirmation_records")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfirmationRecord {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "record_uuid", nullable = false, unique = true, updatable = false)
    private String recordUuid;
    
    private String firstName;
    private String middleName;
    private String lastName;
    private String fathersFullName;
    private String mothersFullName;
    private LocalDate baptismDate;
    private LocalDate confirmationDate;
    private String churchLocation;
    private String placeOfConfirmation;
    private String dioces;
    private String presider;
    private String sponsor;
    private String priestInCharge;
    private Integer bookNo;
    private Integer pageNo;
    private Integer lineNo;
    
    @PrePersist
    public void generateUuid() {
        if (recordUuid == null) {
        	recordUuid = UUID.randomUUID().toString();
        }
    }
    
	@Override
	public String toString() {
		return "ConfirmationRecord [id=" + id + ", recordUuid=" + recordUuid + ", firstName=" + firstName
				+ ", middleName=" + middleName + ", lastName=" + lastName + ", fathersFullName=" + fathersFullName
				+ ", mothersFullName=" + mothersFullName + ", baptismDate=" + baptismDate + ", confirmationDate="
				+ confirmationDate + ", churchLocation=" + churchLocation + ", placeOfConfirmation="
				+ placeOfConfirmation + ", dioces=" + dioces + ", presider=" + presider + ", sponsor=" + sponsor
				+ ", priestInCharge=" + priestInCharge + ", bookNo=" + bookNo + ", pageNo=" + pageNo + ", lineNo="
				+ lineNo + "]";
	}

	public synchronized Long getId() {
		return id;
	}

	public synchronized void setId(Long id) {
		this.id = id;
	}

	public synchronized String getRecordUuid() {
		return recordUuid;
	}

	public synchronized void setRecordUuid(String recordUuid) {
		this.recordUuid = recordUuid;
	}

	public synchronized String getFirstName() {
		return firstName;
	}

	public synchronized void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public synchronized String getMiddleName() {
		return middleName;
	}

	public synchronized void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public synchronized String getLastName() {
		return lastName;
	}

	public synchronized void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public synchronized String getFathersFullName() {
		return fathersFullName;
	}

	public synchronized void setFathersFullName(String fathersFullName) {
		this.fathersFullName = fathersFullName;
	}

	public synchronized String getMothersFullName() {
		return mothersFullName;
	}

	public synchronized void setMothersFullName(String mothersFullName) {
		this.mothersFullName = mothersFullName;
	}

	public synchronized LocalDate getBaptismDate() {
		return baptismDate;
	}

	public synchronized void setBaptismDate(LocalDate baptismDate) {
		this.baptismDate = baptismDate;
	}

	public synchronized LocalDate getConfirmationDate() {
		return confirmationDate;
	}

	public synchronized void setConfirmationDate(LocalDate confirmationDate) {
		this.confirmationDate = confirmationDate;
	}

	public synchronized String getChurchLocation() {
		return churchLocation;
	}

	public synchronized void setChurchLocation(String churchLocation) {
		this.churchLocation = churchLocation;
	}

	public synchronized String getPlaceOfConfirmation() {
		return placeOfConfirmation;
	}

	public synchronized void setPlaceOfConfirmation(String placeOfConfirmation) {
		this.placeOfConfirmation = placeOfConfirmation;
	}

	public synchronized String getDioces() {
		return dioces;
	}

	public synchronized void setDioces(String dioces) {
		this.dioces = dioces;
	}

	public synchronized String getPresider() {
		return presider;
	}

	public synchronized void setPresider(String presider) {
		this.presider = presider;
	}

	public synchronized String getSponsor() {
		return sponsor;
	}

	public synchronized void setSponsor(String sponsor) {
		this.sponsor = sponsor;
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

	public ConfirmationRecord() {
		super();
	}
	
}
