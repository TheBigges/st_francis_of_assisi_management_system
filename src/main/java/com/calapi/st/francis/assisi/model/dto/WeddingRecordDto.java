package com.calapi.st.francis.assisi.model.dto;

import java.time.LocalDate;

public record WeddingRecordDto(
		String groomFirstName, String groomMiddleName, String groomLastName, LocalDate groomDateOfBirth,
		String brideFirstName, String brideMiddleName, String brideLastName, String brideDateOfBirth,
		String witnesses, LocalDate dateOfWedding, String coupleUuid,
		String groomReligion, String brideReligion, String priestInCharge,
		Integer bookNo, Integer pageNo, Integer lineNo
) {
	public static WeddingRecordDto empty() {
		return new WeddingRecordDto(null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null);
	}
}
