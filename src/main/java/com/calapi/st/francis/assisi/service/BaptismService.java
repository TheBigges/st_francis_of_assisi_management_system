package com.calapi.st.francis.assisi.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.jfree.util.Log;
import org.springframework.stereotype.Service;

import com.calapi.st.francis.assisi.dto.mapper.BaptismMapper;
import com.calapi.st.francis.assisi.dto.mapper.ReportsMapper;
import com.calapi.st.francis.assisi.exception.ResourceNotFoundException;
import com.calapi.st.francis.assisi.model.BaptismRecord;
import com.calapi.st.francis.assisi.model.WeddingRecord;
import com.calapi.st.francis.assisi.model.dto.BaptismRecordDto;
import com.calapi.st.francis.assisi.model.dto.WeddingRecordDto;
import com.calapi.st.francis.assisi.repository.BaptismRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BaptismService {

	private final BaptismRepository baptismRepository;
	private final BaptismMapper baptismMapper;
	private final PdfReportService pdfReportService;
	private final ReportsMapper reportsMapper;
	private final String path = "reports/baptismal-pdf-template.jrxml";

	public BaptismService(BaptismRepository baptismRepository, PdfReportService pdfReportService,
			ReportsMapper reportsMapper, BaptismMapper baptismMapper) {
		this.baptismRepository = baptismRepository;
		this.pdfReportService = pdfReportService;
		this.reportsMapper = reportsMapper;
		this.baptismMapper = baptismMapper;
	}
	
	public List<BaptismRecordDto> getBaptismRecords() {
		return Optional.ofNullable(baptismRepository.findAll()).filter(records -> !records.isEmpty())
				.map(baptismMapper::entityToDtoList).orElseGet(Collections::emptyList);
	}
	
	public void postBaptismRecord(BaptismRecordDto baptismRecordDto) {
		if (Objects.isNull(baptismRecordDto)) {
			return;
		}
		try {
			BaptismRecord baptismRecord = baptismMapper.dtoToEntity(baptismRecordDto);
			baptismRepository.save(baptismRecord);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public byte[] generatePDF(String id) {
		Log.info("Now processing child record for " + id);
		BaptismRecord record = baptismRepository.findByChildUuid(id)
				.orElseThrow(() -> new IllegalArgumentException("Baptism record not found: " + id));

		Map<String, Object> params = reportsMapper.baptismReportsMapper(record);
		return pdfReportService.generatePdf(path, params);
	}
	
	@Transactional
	public BaptismRecordDto updateBaptismRecord(String childUuid, BaptismRecordDto baptismRecordDto) {
		var existingRecord = baptismRepository.findByChildUuid(childUuid)
				.orElseThrow(() -> new IllegalArgumentException("Baptism record not found for UUID: " + childUuid));
		baptismMapper.updateEntityFromDto(baptismRecordDto, existingRecord);
		baptismRepository.save(existingRecord);
		return baptismMapper.entityToDto(existingRecord);
	}

	public BaptismRecordDto getBaptismRecordById(String childUuid) {
	    var record = baptismRepository.findByChildUuid(childUuid)
	            .orElseThrow(() -> new ResourceNotFoundException(
	                    "Baptism record not found for UUID: " + childUuid));
	    return baptismMapper.entityToDto(record);
	}
}
