package com.calapi.st.francis.assisi.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.calapi.st.francis.assisi.dto.mapper.ReportsMapper;
import com.calapi.st.francis.assisi.model.dto.ReportDto;
import com.calapi.st.francis.assisi.model.dto.ReportType;

@Service
public class ReportService {
	
	private final PdfReportService pdfReportService;
	private final ReportsMapper reportsMapper;
	private final String path = "reports/baptism_report_pdf_template.jrxml";
	
	public ReportService(PdfReportService pdfReportService,ReportsMapper reportsMapper ) {
		this.pdfReportService = pdfReportService;
		this.reportsMapper = reportsMapper;
	}
	
	public byte[] generateReportPdf(ReportDto reportDto) {
		if(ObjectUtils.isEmpty(reportDto)) {
			new IllegalArgumentException("Error occur during report generation");
		}
		if(reportDto.getReportType().equals(ReportType.BAPTISM)) {
			Map<String, Object> params = reportsMapper.aggregateBaptismReportMapper(reportDto);
			return pdfReportService.generatePdfWithDataSource(path, params);
		}
		return null;
	}

}
