package com.calapi.st.francis.assisi.controller;

import java.time.Year;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.calapi.st.francis.assisi.repository.BaptismRepository;
import com.calapi.st.francis.assisi.service.DashboardService;
import com.calapi.st.francis.assisi.service.RecordService;

@Controller
public class DashboardController {
	
	@Autowired
	RecordService recordService;

	@Autowired
    BaptismRepository baptismRepository;
	
	@Autowired
	DashboardService dashboardService;

	@GetMapping({"/", "/home"})
    public String dashboard(
            @RequestParam(value = "year", required = false) Integer year,
            Model model) {

        int selectedYear = (year != null) ? year : Year.now().getValue();

        model.addAttribute("stats",
                dashboardService.getDashboardStatsByYear(selectedYear));
        model.addAttribute("selectedYear", selectedYear);

        return "dashboard";
    }

    @GetMapping("/confirmation")
    public String confirmation(Model model) {
        model.addAttribute("records", recordService.getConfirmationRecords());
        return "confirmation";
    }
}
