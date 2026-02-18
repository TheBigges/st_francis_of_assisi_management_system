package com.calapi.st.francis.assisi.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jfree.util.Log;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
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

import com.calapi.st.francis.assisi.model.dto.WeddingRecordDto;
import com.calapi.st.francis.assisi.service.WeddingService;

import io.micrometer.common.util.StringUtils;

@Controller
@RequestMapping("/wedding")
public class WeddingController {

	private final WeddingService weddingService;

	public WeddingController(WeddingService weddingService) {
		this.weddingService = weddingService;
	}

	@PostMapping("/save")
	public String postWeddingRecords(@ModelAttribute("weddingRecordDto") WeddingRecordDto weddingRecordDto,
			RedirectAttributes redirectAttributes) {

		try {
			weddingService.postWeddingRecords(weddingRecordDto);
			redirectAttributes.addFlashAttribute("successMessage", "Wedding record successfully saved.");
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while saving the record.");
		}
		return "redirect:/wedding";
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateWeddingRecord(@PathVariable String id, @RequestBody WeddingRecordDto dto) {
		Log.info(String.format("Updating wedding record with uuid %s", id));
		return Optional.ofNullable(weddingService.updateWeddingRecords(id, dto))
				.map(record -> ResponseEntity.ok(record))
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

	}

	@GetMapping
	public String showWeddingForm(Model model, @Param("keyword") String keyword) {

		if (!model.containsAttribute("weddingRecordDto")) {
			model.addAttribute("weddingRecordDto", WeddingRecordDto.empty());
		}
		List<WeddingRecordDto> weddingRecords = weddingService.getWeddingRecords();
		if (!StringUtils.isBlank(keyword) && !ObjectUtils.isEmpty(weddingRecords)) {
		    weddingRecords = weddingRecords.stream()
		        .filter(a -> 
		            (a.groomLastName() != null && a.groomLastName().contains(keyword)) ||
		            (a.brideLastName() != null && a.brideLastName().contains(keyword))
		        )
		        .collect(Collectors.toList());
		}

		model.addAttribute("weddingRecords", weddingRecords);
		return "wedding";
	}

	@GetMapping("/{id}")
	public ResponseEntity<WeddingRecordDto> getWeddingRecordById(@PathVariable String id) {
		WeddingRecordDto weddingRecord = weddingService.getWeddingRecordById(id);
		return ResponseEntity.ok(weddingRecord);
	}
}
