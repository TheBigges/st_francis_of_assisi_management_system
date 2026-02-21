package com.calapi.st.francis.assisi.model.dto;

import java.time.LocalDate;

public record BaptismRecordDto(

    String childUuid,

    String firstName,
    String middleName,
    String lastName,

    LocalDate dateOfBirth,
    String placeOfBirth,
    LocalDate dateOfBaptism,
    String placeOfBaptism,

    String fatherFirstName,
    String fatherMiddleName,
    String fatherLastName,

    String motherFirstName,
    String motherMiddleName,
    String motherLastName,

    String parentsAddress,

    String ageCategory,

    String sponsors,
    String minister,
    String priestInCharge,

    Integer bookNo,
    Integer pageNo,
    Integer lineNo

) {
	public static BaptismRecordDto empty() {
		return new BaptismRecordDto(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,null, null, null, null, null, null);
	}
}
