package com.calapi.st.francis.assisi.model.dto;

import java.time.LocalDate;

public record ConfirmationRecordDto(
    String recordUuid,
    String firstName,
    String middleName,
    String lastName,
    String fathersFullName,
    String mothersFullName,
    LocalDate baptismDate,
    LocalDate confirmationDate,
    String churchLocation,
    String placeOfConfirmation,
    String dioces,
    String presider,
    String sponsor,
    String priestInCharge,
	Integer bookNo, 
	Integer pageNo, 
	Integer lineNo
) {

	public static ConfirmationRecordDto empty() {
		return new ConfirmationRecordDto(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,null);
	}
}