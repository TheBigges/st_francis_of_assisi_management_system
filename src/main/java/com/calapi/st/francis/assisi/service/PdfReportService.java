package com.calapi.st.francis.assisi.service;

import net.sf.jasperreports.engine.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

@Service
public class PdfReportService {

	/**
	 * @return byte[] Generated PDF based on the jrxmlPath
	 * 
	 ***/
	public byte[] generatePdf(String jrxmlPath, Map<String, Object> params) {
		try {
			// Load JRXML
			ClassPathResource reportResource = new ClassPathResource(jrxmlPath);
			InputStream jrxmlStream = reportResource.getInputStream();
			JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
			// Export to PDF
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("Failed to generate PDF report: " + ex.getMessage(), ex);
		}
	}
}