package com.calapi.st.francis.assisi.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.calapi.st.francis.assisi.model.dto.DashboardStatsDto;
import com.calapi.st.francis.assisi.repository.BaptismRepository;
import com.calapi.st.francis.assisi.repository.ConfirmationRepository;
import com.calapi.st.francis.assisi.repository.WeddingRepository;

@Service
public class DashboardService {

	private final BaptismRepository baptismRepository;
	private final WeddingRepository weddingRepository;
	private final ConfirmationRepository confirmationRepository;

	public DashboardService(BaptismRepository baptismRepository, WeddingRepository weddingRepository,
			ConfirmationRepository confirmationRepository) {
		this.baptismRepository = baptismRepository;
		this.weddingRepository = weddingRepository;
		this.confirmationRepository = confirmationRepository;
	}

	public DashboardStatsDto getDashboardStatsByYear(int year) {

		LocalDate startOfYear = LocalDate.of(year, 1, 1);
		LocalDate endOfYear = LocalDate.of(year, 12, 31);

		return new DashboardStatsDto(baptismRepository.countByYear(startOfYear, endOfYear),
				weddingRepository.countByYear(startOfYear, endOfYear), 0, 0);
	}

	public int[] baptismMonthlyCountsByYear(int selectedYear) {
		List<Object[]> results = baptismRepository.countBaptismsPerMonth(selectedYear);
		int[] monthlyCounts = new int[12];
		for (Object[] row : results) {
			int month = (Integer) row[0];
			long count = (Long) row[1];
			monthlyCounts[month - 1] = (int) count;
		}
		return monthlyCounts;
	}
	
	public int[] weddingMonthlyCountsByYear(int selectedYear) {
		List<Object[]> results = weddingRepository.countWeddingsPerMonth(selectedYear);
		int[] monthlyCounts = new int[12];
		for (Object[] row : results) {
			int month = (Integer) row[0];
			long count = (Long) row[1];
			monthlyCounts[month - 1] = (int) count;
		}
		return monthlyCounts;
	}
}
