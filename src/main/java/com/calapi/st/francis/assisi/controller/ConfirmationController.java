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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.calapi.st.francis.assisi.model.dto.ConfirmationRecordDto;
import com.calapi.st.francis.assisi.service.ConfirmationService;

import io.micrometer.common.util.StringUtils;

@Controller
@RequestMapping("/confirmation")
public class ConfirmationController {
	
	private final ConfirmationService confirmationService;

	public ConfirmationController(ConfirmationService confirmationService) {
		this.confirmationService = confirmationService;
	}
	
	@PostMapping("/save")
	public String postConfirmationRecords(@ModelAttribute("confirmationRecordDto") ConfirmationRecordDto confirmationRecordDto,
			RedirectAttributes redirectAttributes) {

		try {
			confirmationService.postConfirmationRecords(confirmationRecordDto);
			redirectAttributes.addFlashAttribute("successMessage", "Confirmation record successfully saved.");
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while saving the record.");
		}
		return "redirect:/confirmation";
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateConfirmationRecord(@PathVariable String id, @RequestBody ConfirmationRecordDto dto) {
		Log.info(String.format("Updating confirmation record with uuid %s", id));
		return Optional.ofNullable(confirmationService.updateConfirmationRecords(id, dto))
				.map(record -> ResponseEntity.ok(record))
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

	}

	@GetMapping
	public String showConfirmationForm(Model model, @Param("keyword") String keyword) {

		if (!model.containsAttribute("confirmationRecordDto")) {
			model.addAttribute("confirmationRecordDto", ConfirmationRecordDto.empty());
		}
		List<ConfirmationRecordDto> confirmationRecords = confirmationService.getConfirmationRecords();
		if (!StringUtils.isBlank(keyword) && !ObjectUtils.isEmpty(confirmationRecords)) {
			confirmationRecords = confirmationRecords.stream()
		        .filter(a -> 
		            (a.lastName() != null && a.lastName().toUpperCase().contains(keyword.toUpperCase()))
		        )
		        .collect(Collectors.toList());
		}

		model.addAttribute("confirmationRecords", confirmationRecords);
		model.addAttribute("keyword", StringUtils.isNotBlank(keyword)?keyword:"");
		return "confirmation";
	}

	@GetMapping("/{id}")
	public ResponseEntity<ConfirmationRecordDto> getConfirmationRecordById(@PathVariable String id) {
		ConfirmationRecordDto confirmationRecords = confirmationService.getConfirmationRecordById(id);
		return ResponseEntity.ok(confirmationRecords);
	}
	
	@GetMapping("/download-pdf/{id}")
	public ResponseEntity<byte[]> downloadPdf(@PathVariable String id) {
		byte[] pdfBytes = confirmationService.generateConfirmationCertificatePdf(id);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("attachment", "document-" + id + ".pdf");

		return ResponseEntity.ok().headers(headers).body(pdfBytes);
	}

}
