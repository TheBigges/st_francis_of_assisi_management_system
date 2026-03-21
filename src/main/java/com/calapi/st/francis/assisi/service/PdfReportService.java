package com.calapi.st.francis.assisi.service;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class PdfReportService {
	

	@Autowired
    private DataSource dataSource;

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
	
	
	public byte[] generatePdfWithDataSource(String jrxmlPath, Map<String, Object> params) {
		try {
			// Load JRXML
			ClassPathResource reportResource = new ClassPathResource(jrxmlPath);
			InputStream jrxmlStream = reportResource.getInputStream();
			JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
			Connection connection = dataSource.getConnection();
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, connection);
			// Export to PDF
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("Failed to generate PDF report: " + ex.getMessage(), ex);
		}
	}
}