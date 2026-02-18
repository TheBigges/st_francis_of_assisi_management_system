package com.calapi.st.francis.assisi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.calapi.st.francis.assisi.model.BaptismRecord;
import com.calapi.st.francis.assisi.model.ConfirmationRecord;
import com.calapi.st.francis.assisi.model.WeddingRecord;
import com.calapi.st.francis.assisi.repository.BaptismRepository;
import com.calapi.st.francis.assisi.repository.ConfirmationRepository;
import com.calapi.st.francis.assisi.repository.WeddingRepository;

/**
 * @implNote This is used to retrieve records shown in the dashboard@
 * 
 * **/
@Service
public class RecordService {

    private final BaptismRepository baptismRepository;
    private final WeddingRepository weddingRepository;
    private final ConfirmationRepository confirmationRepository;

    public RecordService(BaptismRepository baptismRepository,
                         WeddingRepository weddingRepository,
                         ConfirmationRepository confirmationRepository) {
        this.baptismRepository = baptismRepository;
        this.weddingRepository = weddingRepository;
        this.confirmationRepository = confirmationRepository;
    }

    // Baptism
    public List<BaptismRecord> getBaptismRecords() {
        return baptismRepository.findAll();
    }

    // Wedding
    public List<WeddingRecord> getWeddingRecords() {
        return weddingRepository.findAll();
    }

    // Confirmation
    public List<ConfirmationRecord> getConfirmationRecords() {
        return confirmationRepository.findAll();
    }

    // You can add create/update/delete methods as needed here
}
