package com.calapi.st.francis.assisi.model.dto;

import java.time.LocalDate;

public class ReportDto {

	private ReportType reportType;
	private String reportYear;
	private LocalDate dateFrom;
	private LocalDate dateTo;
	
	public ReportType getReportType() {
		return reportType;
	}
	public void setReportType(ReportType reportType) {
		this.reportType = reportType;
	}
	public String getReportYear() {
		return reportYear;
	}
	public void setReportYear(String reportYear) {
		this.reportYear = reportYear;
	}
	public LocalDate getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}
	public LocalDate getDateTo() {
		return dateTo;
	}
	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}
	
	@Override
	public String toString() {
		return "ReportDto [reportType=" + reportType + ", reportYear=" + reportYear + ", dateFrom=" + dateFrom
				+ ", dateTo=" + dateTo + "]";
	}
	
	public ReportDto() {
		super();
	}
	
}
