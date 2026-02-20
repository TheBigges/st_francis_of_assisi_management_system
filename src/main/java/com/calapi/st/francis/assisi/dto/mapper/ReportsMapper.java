package com.calapi.st.francis.assisi.dto.mapper;

import static com.calapi.st.francis.assisi.util.CommonUtils.formatDate;
import static com.calapi.st.francis.assisi.util.CommonUtils.getFirstTwoTokens;
import static com.calapi.st.francis.assisi.util.CommonUtils.getMonthName;
import static com.calapi.st.francis.assisi.util.CommonUtils.getOrdinalDay;
import static com.calapi.st.francis.assisi.util.CommonUtils.getYear;
import static com.calapi.st.francis.assisi.util.CommonUtils.joinNames;
import static com.calapi.st.francis.assisi.util.CommonUtils.nullSafe;
import static com.calapi.st.francis.assisi.util.CommonUtils.nullSafeNumber;
import static com.calapi.st.francis.assisi.util.CommonUtils.toUpper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.calapi.st.francis.assisi.model.BaptismRecord;
import com.calapi.st.francis.assisi.model.ConfirmationRecord;
import com.calapi.st.francis.assisi.model.WeddingRecord;

@Component
public class ReportsMapper {
	/**
	 * @return Map<String, Object> of parameters defined in iReports creation for
	 *         Baptismal Certificate creation
	 * 
	 **/
	public Map<String, Object> baptismReportsMapper(BaptismRecord record) {
		Map<String, Object> params = new HashMap<>();
		params.put("CHILD_NAME",
				toUpper(joinNames(record.getFirstName(), record.getMiddleName(), record.getLastName())));
		params.put("DATE_OF_BIRTH", toUpper(formatDate(record.getDateOfBirth())));
		params.put("PLACE_OF_BIRTH", toUpper(nullSafe(record.getPlaceOfBirth())));
		params.put("NAME_OF_FATHER", toUpper(
				joinNames(record.getFatherFirstName(), record.getFatherMiddleName(), record.getFatherLastName())));
		params.put("NAME_OF_MOTHER", toUpper(
				joinNames(record.getMotherFirstName(), record.getMotherMiddleName(), record.getMotherLastName())));
		params.put("PARENTS_RESIDENCE", toUpper(nullSafe(record.getParentsAddress())));
		params.put("DATE_OF_BAPTISM", toUpper(formatDate(record.getDateOfBaptism())));
		params.put("MINISTER_OF_BAPTISM", toUpper(nullSafe(record.getMinister())));
		params.put("PRIEST_IN_CHARGE", toUpper(nullSafe(record.getPriestInCharge())));
		params.put("SPONSOR", toUpper(nullSafe(record.getSponsors())));
		params.put("BOOK_NO", nullSafeNumber(record.getBookNo()));
		params.put("PAGE_NO", nullSafeNumber(record.getPageNo()));
		params.put("LINE_NO", nullSafeNumber(record.getLineNo()));

		return params;
	}
	/**
	 * @return Map<String, Object> of parameters defined in iReports creation for
	 *         Wedding Certificate creation
	 * 
	 **/
	public Map<String, Object> weddingReportMapper(WeddingRecord record) {
		Map<String, Object> params = new HashMap<>();
		params.put("GROOM_P",
				toUpper(joinNames(record.getGroomFirstName(), record.getGroomMiddleName(), record.getGroomLastName())));
		params.put("BRIDE_P",
				toUpper(joinNames(record.getBrideFirstName(), record.getBrideMiddleName(), record.getBrideLastName())));
		params.put("DAY_P", toUpper(getOrdinalDay(record.getDateOfWedding())));
		params.put("MONTH_P", toUpper(getMonthName(record.getDateOfWedding())));
		params.put("YEAR_P", getYear(record.getDateOfWedding()));
		params.put("BOOK_NO_P", nullSafeNumber(record.getBookNo()));
		params.put("PAGE_NO_P", nullSafeNumber(record.getPageNo()));
		params.put("LINE_NO_P", nullSafeNumber(record.getLineNo()));
		params.put("PRIEST_IN_CHARGE_P", toUpper(record.getPriestInCharge()));
		List<String> witnesses = getFirstTwoTokens(nullSafe(record.getWitnesses()));
		if (!witnesses.isEmpty()) {
			params.put("WITNESS1_P", nullSafe(witnesses.get(0)));
			params.put("WITNESS2_P", nullSafe(witnesses.get(1)));
		}
		return params;
	}
	/**
	 * @return Map<String, Object> of parameters defined in iReports creation for
	 *         Confirmation Certificate creation
	 * 
	 **/
	public Map<String, Object> confirmationReportMapper(ConfirmationRecord record) {
		Map<String, Object> params = new HashMap<>();
		params.put("P_FULL_NAME",
				toUpper(joinNames(record.getFirstName(), record.getMiddleName(), record.getLastName())));
		params.put("P_FATHER", toUpper(nullSafe(record.getFathersFullName())));
		params.put("P_MOTHER", toUpper(nullSafe(record.getMothersFullName())));
		params.put("P_BAPTISM_DATE", toUpper(formatDate(record.getBaptismDate())));
		params.put("P_CONFIRMATION_DATE", toUpper(formatDate(record.getConfirmationDate())));
		params.put("P_PLACE", toUpper(nullSafe(record.getPlaceOfConfirmation())));
		params.put("P_DIOCES", toUpper(nullSafe(record.getDioces())));
		params.put("P_PRECIDER", toUpper(nullSafe(record.getPresider())));
		params.put("P_SPONSOR", toUpper(nullSafe(record.getSponsor())));
		params.put("P_PRIEST_IN_CHARGE", toUpper(nullSafe(record.getPriestInCharge())));
		params.put("P_CHURCH", toUpper(nullSafe(record.getChurchLocation())));
		return params;
	}
}
