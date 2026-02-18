package com.calapi.st.francis.assisi.service;

import java.time.LocalDate;

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

    public DashboardService(
            BaptismRepository baptismRepository,
            WeddingRepository weddingRepository,
            ConfirmationRepository confirmationRepository) {
        this.baptismRepository = baptismRepository;
        this.weddingRepository = weddingRepository;
        this.confirmationRepository = confirmationRepository;
    }

    public DashboardStatsDto getDashboardStatsByYear(int year) {

        LocalDate startOfYear = LocalDate.of(year, 1, 1);
        LocalDate endOfYear = LocalDate.of(year, 12, 31);

        return new DashboardStatsDto(
                baptismRepository.countByYear(startOfYear, endOfYear),
                weddingRepository.countByYear(startOfYear, endOfYear),
                0,
                0
        );
    }
}

