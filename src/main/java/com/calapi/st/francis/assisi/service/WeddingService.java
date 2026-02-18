package com.calapi.st.francis.assisi.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.calapi.st.francis.assisi.dto.mapper.ReportsMapper;
import com.calapi.st.francis.assisi.dto.mapper.WeddingMapper;
import com.calapi.st.francis.assisi.model.WeddingRecord;
import com.calapi.st.francis.assisi.model.dto.WeddingRecordDto;
import com.calapi.st.francis.assisi.repository.WeddingRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WeddingService {

	private final WeddingMapper weddingMapper;
	private final PdfReportService pdfReportService;
	private final WeddingRepository weddingRepository;
	private final ReportsMapper reportsMapper;
	private final String path = "reports/wedding-pdf-template.jrxml";;

	public WeddingService(WeddingMapper weddingMapper, PdfReportService pdfReportService,
			WeddingRepository weddingRepository, ReportsMapper reportsMapper) {
		this.weddingMapper = weddingMapper;
		this.pdfReportService = pdfReportService;
		this.weddingRepository = weddingRepository;
		this.reportsMapper = reportsMapper;
	}

	public List<WeddingRecordDto> getWeddingRecords() {
		return Optional.ofNullable(weddingRepository.findAll()).filter(records -> !records.isEmpty())
				.map(weddingMapper::entityToDtoList).orElseGet(Collections::emptyList);
	}
	
	public WeddingRecordDto getWeddingRecordById(String coupleUuid) {
	    var existingRecord = weddingRepository.findByCoupleUuid(coupleUuid)
	            .orElseThrow(() -> new EntityNotFoundException(
	                    "Wedding record not found for UUID: " + coupleUuid));
	    return weddingMapper.entityToDto(existingRecord);
	}

	public void postWeddingRecords(WeddingRecordDto weddingRecordDto) {
		if (Objects.isNull(weddingRecordDto)) {
			return;
		}
		try {
			WeddingRecord weddingRecord = weddingMapper.dtoToEntity(weddingRecordDto);
			weddingRepository.save(weddingRecord);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public WeddingRecordDto updateWeddingRecords(String coupleUuid, WeddingRecordDto weddingRecordDto) {
		var existingRecord = weddingRepository.findByCoupleUuid(coupleUuid)
				.orElseThrow(() -> new IllegalArgumentException("Wedding record not found for UUID: " + coupleUuid));
		weddingMapper.updateEntityFromDto(weddingRecordDto, existingRecord);
		weddingRepository.save(existingRecord);
		return getWeddingRecordById(coupleUuid);
	}

	@Transactional
	public byte[] generateWeddingCertificatePdf(String coupleUuid) {
		var existingRecord = weddingRepository.findByCoupleUuid(coupleUuid)
				.orElseThrow(() -> new IllegalArgumentException("Wedding record not found for UUID: " + coupleUuid));
		
		Map<String, Object> params = reportsMapper.weddingReportMapper(existingRecord);
		return pdfReportService.generatePdf(path, params);

	}

}
