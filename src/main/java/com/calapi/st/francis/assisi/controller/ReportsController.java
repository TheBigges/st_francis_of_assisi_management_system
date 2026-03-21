package com.calapi.st.francis.assisi.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.calapi.st.francis.assisi.model.dto.ReportDto;
import com.calapi.st.francis.assisi.service.ReportService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/reports")
public class ReportsController {
	
	private final ReportService reportService;

	public ReportsController(ReportService reportService) {
		this.reportService = reportService;
	}
	
	 @GetMapping
	    public String reportPage() {
	        return "reports";
	    }

	
	@PostMapping("/download-pdf")
	public ResponseEntity<byte[]> downloadPdf(ReportDto reportDto) {
		System.out.println("Generating report for "+reportDto.getReportType());
		byte[] pdfBytes = reportService.generateReportPdf(reportDto);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("attachment","Report-document-"+reportDto.getReportType().name()+".pdf");
		return ResponseEntity.ok().headers(headers).body(pdfBytes);
	}

}
