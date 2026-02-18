package com.calapi.st.francis.assisi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.calapi.st.francis.assisi.model.ConfirmationRecord;

public interface ConfirmationRepository extends JpaRepository<ConfirmationRecord, Long> {}
