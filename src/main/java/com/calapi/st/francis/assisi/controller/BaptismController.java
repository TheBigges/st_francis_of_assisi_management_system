package com.calapi.st.francis.assisi.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jfree.util.Log;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.calapi.st.francis.assisi.model.dto.BaptismRecordDto;
import com.calapi.st.francis.assisi.service.BaptismService;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/baptism")
public class BaptismController {


	private final BaptismService baptismService;
	public BaptismController(BaptismService baptismService) {
		this.baptismService = baptismService;
	}

	@GetMapping
	public String showBaptismPage(Model model, @Param("keyword") String keyword) {
		if (!model.containsAttribute("baptismRecordDto")) {
			model.addAttribute("baptismRecordDto", BaptismRecordDto.empty());
		}

		List<BaptismRecordDto> baptismRecords = baptismService.getBaptismRecords();
		if (!StringUtils.isBlank(keyword) && !ObjectUtils.isEmpty(baptismRecords)) {
			baptismRecords = baptismRecords.stream()
					.filter(a -> (a.lastName() != null && a.lastName().toUpperCase().contains(keyword.toUpperCase())))
					.collect(Collectors.toList());
		}
		model.addAttribute("baptismRecods", baptismRecords);
		model.addAttribute("keyword", StringUtils.isNotBlank(keyword) ? keyword : "");
		return "baptism";
	}

	@GetMapping("/{id}")
	public ResponseEntity<BaptismRecordDto> getBaptismById(@PathVariable("id") String id) {
		return ResponseEntity.ok(baptismService.getBaptismRecordById(id));
	}

	@PostMapping("/save")
	public String addBaptismRecord(BaptismRecordDto baptismRecordDto, RedirectAttributes redirectAttributes) {
		Log.info(String.format("New record has been received %s", baptismRecordDto.toString()));
		try {
			baptismService.postBaptismRecord(baptismRecordDto);
			redirectAttributes.addFlashAttribute("successMessage", "Baptism record successfully saved.");
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while saving the record.");
		}
		return "redirect:/baptism";
	}

	@PutMapping("/update/{childId}")
	public ResponseEntity<?> updateBaptismRecord(@PathVariable String childId, @RequestBody BaptismRecordDto dto) {
		Log.info(String.format("Updating wedding record with uuid %s", childId));
		return Optional.ofNullable(baptismService.updateBaptismRecord(childId, dto))
				.map(record -> ResponseEntity.ok(record))
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("/download-pdf/{id}")
	public ResponseEntity<byte[]> downloadPdf(@PathVariable String id) {
		byte[] pdfBytes = baptismService.generatePDF(id);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("attachment", "document-" + id + ".pdf");

		return ResponseEntity.ok().headers(headers).body(pdfBytes);
	}

}
