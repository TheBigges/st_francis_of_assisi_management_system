package com.calapi.st.francis.assisi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.calapi.st.francis.assisi.dto.mapper.BaptismMapper;
import com.calapi.st.francis.assisi.model.BaptismRecord;
import com.calapi.st.francis.assisi.model.dto.BaptismRecordDto;
import com.calapi.st.francis.assisi.repository.BaptismRepository;
import com.calapi.st.francis.assisi.service.BaptismService;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BaptismController {

	@Autowired
	BaptismRepository baptismRepository;

	@Autowired
	BaptismService baptismService;

	@GetMapping("/baptism")
	public String showBaptismPage(Model model, @Param("keyword") String keyword) {
		List<BaptismRecord> records = new ArrayList<>();
		baptismRepository.findAll().forEach(records::add);

		if (!StringUtils.isBlank(keyword) && !ObjectUtils.isEmpty(records)) {
			records = records.stream().filter(
					a -> a.getLastName() != null && keyword != null && a.getLastName().equalsIgnoreCase(keyword))
					.collect(Collectors.toList());

		}

		List<BaptismRecordDto> baptismRecords = BaptismMapper.INSTANCE.entityToDtoList(records);
		model.addAttribute("baptismRecordDto", new BaptismRecordDto());
		model.addAttribute("baptismRecods", baptismRecords);
		model.addAttribute("keyword", StringUtils.isNotBlank(keyword)?keyword:"");
		return "baptism";
	}

	@GetMapping("/baptism/{id}")
	public ResponseEntity<BaptismRecordDto> getBaptismById(@PathVariable String id) {
		return baptismRepository.findByChildUuid(id).map(child -> {
			// Convert entity to DTO
			BaptismRecordDto dto = BaptismMapper.INSTANCE.entityToDto(child);
			return ResponseEntity.ok(dto);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/baptism/save")
	public String addBaptismRecord(BaptismRecordDto baptismRecordDto, RedirectAttributes redirectAttributes) {
		Log.info("New record has been received "+baptismRecordDto.toString());
		boolean recordExists = baptismRepository.existsDuplicate(baptismRecordDto);

		if (recordExists) {
			Log.info("Existing record found");
			redirectAttributes.addFlashAttribute("message", "A baptism record with the same details already exists.");
			return "redirect:/baptism";
		}

		try {
			BaptismRecord baptismRecord = BaptismMapper.INSTANCE.dtoToEntity(baptismRecordDto);

			baptismRepository.save(baptismRecord);

			redirectAttributes.addFlashAttribute("message", "The baptism record has been saved successfully.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "An error occurred during processing.");
		}

		return "redirect:/baptism";
	}

	@PutMapping("/baptism/update/{childId}")
	public ResponseEntity<?> updateBaptismRecord(@PathVariable String childId, @RequestBody BaptismRecordDto dto) {

		return baptismRepository.findByChildUuid(childId).map(existing -> {
			existing.setFirstName(dto.getFirstName());
			existing.setMiddleName(dto.getMiddleName());
			existing.setLastName(dto.getLastName());
			existing.setDateOfBirth(dto.getDateOfBirth());
			existing.setPlaceOfBirth(dto.getPlaceOfBirth());
			existing.setDateOfBaptism(dto.getDateOfBaptism());
			existing.setPlaceOfBaptism(dto.getPlaceOfBaptism());
			existing.setAgeCategory(dto.getAgeCategory());

			existing.setFatherFirstName(dto.getFatherFirstName());
			existing.setFatherMiddleName(dto.getFatherMiddleName());
			existing.setFatherLastName(dto.getFatherLastName());

			existing.setMotherFirstName(dto.getMotherFirstName());
			existing.setMotherMiddleName(dto.getMotherMiddleName());
			existing.setMotherLastName(dto.getMotherLastName());
			existing.setParentsAddress(dto.getParentsAddress());

			existing.setSponsors(dto.getSponsors());
			existing.setMinister(dto.getMinister());
			existing.setPriestInCharge(dto.getPriestInCharge());

			existing.setBookNo(dto.getBookNo());
			existing.setPageNo(dto.getPageNo());
			existing.setLineNo(dto.getLineNo());

			BaptismRecord updated = baptismRepository.save(existing);

			return ResponseEntity.ok(updated);
		}).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaptismRecord()));
	}

	@GetMapping("/baptism/download-pdf/{id}")
	public ResponseEntity<byte[]> downloadPdf(@PathVariable String id) {
		byte[] pdfBytes = baptismService.generatePDF(id);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("attachment", "document-" + id + ".pdf");

		return ResponseEntity.ok().headers(headers).body(pdfBytes);
	}

}
