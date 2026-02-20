package com.calapi.st.francis.assisi.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.calapi.st.francis.assisi.dto.mapper.ConfirmationMapper;
import com.calapi.st.francis.assisi.dto.mapper.ReportsMapper;
import com.calapi.st.francis.assisi.model.ConfirmationRecord;
import com.calapi.st.francis.assisi.model.dto.ConfirmationRecordDto;
import com.calapi.st.francis.assisi.repository.ConfirmationRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConfirmationService {

	private final ConfirmationMapper confirmationMapper;
	private final PdfReportService pdfReportService;
	private final ConfirmationRepository confirmationRepository;
	private final ReportsMapper reportsMapper;
	private final String path = "reports/confirmation-pdf-template.jrxml";

	public ConfirmationService(ConfirmationMapper confirmationMapper, PdfReportService pdfReportService,
			ConfirmationRepository confirmationRepository, ReportsMapper reportsMapper) {
		this.confirmationMapper = confirmationMapper;
		this.pdfReportService = pdfReportService;
		this.confirmationRepository = confirmationRepository;
		this.reportsMapper = reportsMapper;
	}
	
	public List<ConfirmationRecordDto> getConfirmationRecords() {
		return Optional.ofNullable(confirmationRepository.findAll()).filter(records -> !records.isEmpty())
				.map(confirmationMapper::entityToDtoList).orElseGet(Collections::emptyList);
	}
	
	public ConfirmationRecordDto getConfirmationRecordById(String coupleUuid) {
	    var existingRecord = confirmationRepository.findByRecordUuid(coupleUuid)
	            .orElseThrow(() -> new EntityNotFoundException(
	                    "Confirmation record not found for UUID: " + coupleUuid));
	    return confirmationMapper.entityToDto(existingRecord);
	}

	public void postConfirmationRecords(ConfirmationRecordDto confirmationRecordDto) {
		if (Objects.isNull(confirmationRecordDto)) {
			return;
		}
		try {
			ConfirmationRecord confirmationRecord = confirmationMapper.dtoToEntity(confirmationRecordDto);
			confirmationRepository.save(confirmationRecord);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public ConfirmationRecordDto updateConfirmationRecords(String recordUuid, ConfirmationRecordDto confirmationRecordDto) {
		var existingRecord = confirmationRepository.findByRecordUuid(recordUuid)
				.orElseThrow(() -> new IllegalArgumentException("Confirmation record not found for UUID: " + recordUuid));
		confirmationMapper.updateEntityFromDto(confirmationRecordDto, existingRecord);
		confirmationRepository.save(existingRecord);
		return getConfirmationRecordById(recordUuid);
	}

	@Transactional
	public byte[] generateConfirmationCertificatePdf(String recordUuid) {
		var existingRecord = confirmationRepository.findByRecordUuid(recordUuid)
				.orElseThrow(() -> new IllegalArgumentException("Confirmation record not found for UUID: " + recordUuid));
		
		Map<String, Object> params = reportsMapper.confirmationReportMapper(existingRecord);
		return pdfReportService.generatePdf(path, params);

	}
}
