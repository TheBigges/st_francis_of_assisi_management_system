package com.calapi.st.francis.assisi.service;

import java.util.Map;

import org.jfree.util.Log;
import org.springframework.stereotype.Service;

import com.calapi.st.francis.assisi.dto.mapper.ReportsMapper;
import com.calapi.st.francis.assisi.model.BaptismRecord;
import com.calapi.st.francis.assisi.repository.BaptismRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BaptismService {

	private final BaptismRepository baptismRepository;
	private final PdfReportService pdfReportService;
	private final ReportsMapper reportsMapper;
	private final String path = "reports/baptismal-pdf-template.jrxml";

	public BaptismService(BaptismRepository baptismRepository, PdfReportService pdfReportService,
			ReportsMapper reportsMapper) {
		this.baptismRepository = baptismRepository;
		this.pdfReportService = pdfReportService;
		this.reportsMapper = reportsMapper;
	}

	@Transactional
	public byte[] generatePDF(String id) {
		Log.info("Now processing child record for " + id);
		BaptismRecord record = baptismRepository.findByChildUuid(id)
				.orElseThrow(() -> new IllegalArgumentException("Baptism record not found: " + id));

		Map<String, Object> params = reportsMapper.baptismReportsMapper(record);
		return pdfReportService.generatePdf(path, params);
	}

}
